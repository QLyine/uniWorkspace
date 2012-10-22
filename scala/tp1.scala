object MatrixOps {

    type Row = Stream[Int]
    type Matrix = Stream[Row]

    private val zeroRow: Row = Stream.cons(0, zeroRow)
    private val intsRow: Row = Stream.cons(0, intsRow.map(_+1))
    private def addRow(a: Row, b: Row): Row =
                Stream.cons(a.head + b.head, addRow(a.tail, b.tail))

    private def one_in_nth_pos(x:Int):Row = if ( x == 0 ) Stream.cons(1, one_in_nth_pos(x-1)) else Stream.cons(0, one_in_nth_pos(x-1) )
    private def matrix_diag_one(x:Int):Matrix = Stream.cons(one_in_nth_pos(x), matrix_diag_one(x+1))
    val diagDir: Matrix = matrix_diag_one(0)

    private def shiftOne(r : Row): Row = Stream.cons(0, r)
    val diagCir: Matrix = Stream.cons(Stream.cons(1, zeroRow), diagCir.map(shiftOne(_) ) )

    def add_zero(x: Int): Row =
    {
      Stream.cons(0, Stream.from(1) map( e => e * x ) )
    }
  
    val multCir: Matrix = Stream.cons(Stream.cons(0, zeroRow), multCir map ( e => add_zero( (e drop 1 head) + 1) ) ) 

    private def multGen(x: Int): Matrix = Stream.cons(add_zero(x), multGen(x+1) ) 

    val multDir: Matrix = multGen(0) 

    val zero: Matrix = Stream.cons(zeroRow, zero)
    
    val subCir: Matrix = Stream.cons(intsRow map (_ * -1), subCir map ( _ map ( _ + 1) ) )

    private def sqCir_aux(x: Int): Row = 
    {
      (Stream.fill(x+1)(x) ++ Stream.from(x+1))
    }

    val sqCir: Matrix = Stream.cons(Stream.from(0), sqCir map ( e => sqCir_aux(e.head+1) ) )

    def add(a: Matrix, b: Matrix): Matrix = Stream.cons(addRow(a.head, b.head), add(a.tail, b.tail))

    def mult(m: Matrix, s: Int): Matrix = m map ( _ map ( _ * s) ) 
    
    def trans(m: Matrix): Matrix = Stream.cons(m map (_.head), trans(m map (_.tail) ) )  

    def findInRow(r: Row, z: Int): Int = 
    {
      r.zipWithIndex filter { case (x,v) => x == z } match 
      {
        case (xx,vv) #:: xs => xx
        case _ => -1
      }
    }

    def _find(m: Matrix, z:Int, l: Int): (Int, Int) =
    {
      m match 
      {
        case x #:: xs => lazy val a = findInRow(x, z); if( a != -1 ) (l, a) else _find(xs, z, l + 1)
        case _ => (-1,-1)
      }
    }
    
    def find(m: Matrix, z:Int) : (Int, Int) =
    {
      _find(m, z, 0)
    }

    def _make(f: (Int, Int) => Int, l: Int ) : Matrix  = Stream.cons(Stream.from(0) map ( f(l,_) ), _make(f, l+1))  
    
    def make (f : (Int, Int) => Int ): Matrix = _make(f,0)

}

object MatrixTests {
    import MatrixOps._
    def main(args: Array[String]): Unit = {
        val m: Matrix = MatrixOps.zero
        println(m)
    }

  def print_n(m: Matrix, x:Int): Unit =
  {
    m take x foreach ( e => println( e take x print ) )
  }

  def transform_to_finite(m: Matrix, i: Int, j: Int): Matrix = 
  {
    m take i map ( _ take j ) 
  }

  def sum(x: Int, y: Int) = x + y
}

import MatrixOps._
import MatrixTests._
