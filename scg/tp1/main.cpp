/*
 * =====================================================================================
 *
 *       Filename:  main.cpp
 *
 *    Description:  TP1 - 2
 *
 *        Version:  1.0
 *        Created:  09/23/2012 08:41:00 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  YOUR NAME (), 
 *        Company:  
 *
 * =====================================================================================
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <map>
#include <utility>

using namespace std; 

int main(int argc, char* argv[]) {
  
  // declare variables

  ifstream fi;
  ofstream fo;

  char delims [] = "\",;:|/\\.!()?_ ";
  char *res;

  string line;

  if( argc < 2)
  {
    cout << "Invalid number of arguments" << endl;
    // cout << "Usage " + argv[0] + " [file1 ... file n]"
    printf("Usage : %s [file1 ... file n] \n", argv[0]);
    exit(1);
  }

  // declare maps

  map <string, map <string, int> > m;
  map <string, map <string, int> >::iterator mi;
  map <string, int>::iterator mii;

  // read all files and word count each

  for( int i = 1; i < argc; i ++)
  {
    m[argv[i]] = map<string, int>();
  }

  for( mi = m.begin(); mi != m.end(); ++mi )
  {
  //  cout << "Key: '" << iter->first << "', Value: " << iter->second << endl; 
    fi.open(mi->first.c_str());

    while( fi.is_open() && !fi.eof() )
    {
      getline(fi, line);
      res = strtok((char *) line.c_str(), delims);
      while( res != NULL )
      {
        if( strlen(res) >= 3 ) mi->second[string(res)] ++;
        res = strtok(NULL, delims);
      }
    }

    fi.close();
  }

  // output result

  ofstream fout("result.txt", ios::app); 

  for( mi = m.begin(); mi != m.end(); ++mi )
  {
    cout << mi->first << " : " << endl;
    fout << mi->first << " : " << endl;
    for( mii = mi->second.begin(); mii != mi->second.end(); ++mii )
    {
      cout << "\t " << mii->first << " : " << mii->second << endl;
      fout << "\t " << mii->first << " : " << mii->second << endl;
    }
  }

  return 0;
} 
