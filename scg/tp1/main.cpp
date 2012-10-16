#include <mpi.h>
#include <iostream>
#include <algorithm>
#include <fstream>
#include <string.h>
#include <stdio.h>
#include <map>
#include <vector>
#include <stdlib.h>

#define TAG 0

using namespace std;

/* Local functions */

static void master(void);
static void slave(void);
static void process_results(string result);
static string do_work(string work);
static char *getMsg(int id);
static void sendMsg(int id, char *msg);

int numprocs;
int myrank;

char ** filesList;
int numberDocs;

char *getMsg(int id)
{

  int msg_size;

  char *buff;

  MPI_Status stat;
  MPI_Probe(id, TAG, MPI_COMM_WORLD, &stat);
  MPI_Get_count(&stat, MPI_CHAR, &msg_size);
  
  buff = (char *) calloc(msg_size, sizeof(char)); 

  MPI_Recv(buff, msg_size, MPI_CHAR, id, TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

  return buff;
}

void sendMsg(int id, char *msg)
{

  MPI_Send(msg, strlen(msg), MPI_CHAR, id, TAG, MPI_COMM_WORLD);
}

int main(int argc, char* argv[])
{



  /* Initialize MPI */

  MPI_Init(&argc, &argv);

  /* Find out my identity in the default communicator */

  MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
  MPI_Comm_size(MPI_COMM_WORLD,&numprocs);

  if( argc < 2)
  {
    cout << "Invalid number of arguments" << endl;
    // cout << "Usage " + argv[0] + " [file1 ... file n]"
    printf("Usage : %s [file1 ... file n] \n", argv[0]);
    exit(1);
  }

  numberDocs = argc - 1;
  filesList = &argv[1];

  if (myrank == 0) {
    master();
  } else {
    slave();
  }

  /* Shut down MPI */

  MPI_Finalize();
  return 0;
}

static void
master(void)
{
  char end[] = "-";
  vector<string> res;
  char *buff;
  int i, docsDone = 0, docsSent = 0; 
 
  MPI_Status stat;

  printf("->: We have %d processors and %d slaves \n", numprocs, (numprocs - 1));

  printf("->: Waiting for Slaves .... \n");
  MPI_Barrier(MPI_COMM_WORLD);

  printf("->: Sending jobs to each slave \n");

  while( (numberDocs - docsDone) > 0 )
  {
    for(i=1;i<min(numprocs,(numberDocs - docsSent)+1);i++)
    {
      //METER A ENVIAR OUTROS FICHEIROS
      sendMsg(i, filesList[docsSent]);
      docsSent ++;

    }
    printf("->: Jobs sent %d \n", docsSent);
    printf("->: Number of docs to send  %d \n", (numberDocs - docsSent) );
    for(i=1;i<min(numprocs,(docsSent - docsDone)+1);i++)
    {

      buff = getMsg(i);

      printf("->: %s\n", buff);
      docsDone ++;
      res.push_back( string(buff) );
      
      free(buff);
    }
  }


  for(i = 1; i < numprocs; i++)
  {
    sendMsg(i, end);
  }

  printf("->: Commanded all slaves to end\n");  

}

static void
slave(void)
{
  char *buff;
  int numprocs;
  int cmp = 1;

  MPI_Status stat;
  MPI_Barrier(MPI_COMM_WORLD);
  /* receive from rank 0: */

  buff = getMsg(0);
  cmp = strcmp(buff, "-");

  while( cmp != 0 )
  {
    string res = do_work(buff);

    res += "\n";

    sendMsg(0, (char *) res.c_str());

    buff = getMsg(0);
    cmp = strcmp(buff, "-");
  }

}


static void 
process_results(string result)
{
  //METER AQUI COISAS
}


static string
do_work(string work)
{
  	map<string,int> m;
	ifstream myReadFile;
	char delims [] = "\",;:|/\\.!()?_ ";

	myReadFile.open("sample.txt"); //ler do argumento
	char output[100];

	if(myReadFile.is_open())
	{
		string last; //trafulhice

		while(!myReadFile.eof())
		{
			myReadFile >> output;
			char* res = strtok(output, delims);
			last = string(res); //trafulhice
			if(res != NULL && strlen(res) >= 3)
			{
				
				string str(res);
				m[str]++;
			}
		}

		if(strlen(last.c_str()) >= 3) //trafulhice
			m[last]--;

	}

	myReadFile.close();
	string res = work + ";";
	map<string,int>::iterator it;
	for ( it=m.begin() ; it != m.end(); it++ )
	{
		char buf[5];
		
		snprintf(buf, sizeof(buf), "%d", (*it).second);
		res = res + (*it).first + ":" + buf + ";";
	}

	return res;
}
