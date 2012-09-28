abstract class ZList[+T]
case object ZNil extends ZList[Nothing]
case class ZNode[T](head: T, tail: Unit=>ZList[T]) extends ZList[T]

val z123 = ZNode(1, u=>ZNode(2, u=>ZNode(3, u=>ZNil))):ZList[Int]

def zhd[T](l: ZList[T]):T =
{
  l match 
  {
    case ZNil => error ("zhd: Head of Empty List")
                              case ZNode(x, _) => x
  }
}

def ztl[T](l: ZList[T]):ZList[T] =
{
  l match {
    case ZNil => error("ztl: tail of empty zlist")
                             case ZNode(x,xs) => xs()
  }
}

def ztake[T](v:Int, l:ZList[T]):ZList[T] =
{
  l match 
  {
    case ZNil => ZNil
      case ZNode(x, xs) => if ( v <= 0 ) ZNil else ZNode(x, u=>ztake(v-1, xs()))
  }
}

def zdrop[T](v:Int, l:ZList[T]):ZList[T] =
{
  l match 
  {
    case ZNil => ZNil
      case ZNode(x, xs) => if ( v <= 0 ) ZNode(x, u=>zdrop(v-1, xs())) else zdrop(v-1, xs())
  }
}

def zindex[T](v:Int, l:ZList[T]):T = zhd( zdrop(v, l) )

def zappend[T](l1:ZList[T], l2:ZList[T]):ZList[T] =
{
  l1 match 
  {
    case ZNil => l2
    case ZNode(x, xs) => ZNode(x, u=>zappend(xs(), l2)) 
  } 
}

def zshow[T](l: ZList[T], n: Int): List[T] =
{
    if (n == 0) Nil
    else l match {
        case ZNil => Nil
        case ZNode(x,xs) => x::zshow(xs(), n-1)
    }
}

def zcount_from(n: Int): ZList[Int] = ZNode(n, u=>zcount_from(n+1))

val zints = zcount_from(1)

def zmul_three_main(x:Int):ZList[Int] = ZNode(x*3, u=>zmul_three_main(x+1))

val zmul_three = zmul_three_main(1)

def zzeros_main(x : Int) : ZList[Int] = ZNode(0, u=>zzeros_main(0))

val zzeros = zzeros_main(0)

def bit(x:Int): Int = if( (x & 1) == 1 ) 1 else 0

def zzeros_ones_main (x: Int) : ZList[Int] = ZNode( bit(x), u=>zzeros_ones_main(x+1))

val zzeros_ones = zzeros_ones_main( 0 )

def zfib_main(a:Int, b:Int):ZList[Int] = ZNode(a + b, u=>zfib_main(b, a + b ) )

val zfib = zfib_main(0,1)

def zfilter[T](l: ZList[T], b: T=>Boolean): ZList[T] =
{
    l match {
        case ZNil => ZNil
        case ZNode(x,xs) =>
                if (b(x)) ZNode(x, u=>zfilter(xs(), b))
                else zfilter(xs(), b)
    }
}

def zprimes_gen(nums: ZList[Int]) : ZList[Int] = 
  ZNode( zhd(nums), u=>( zprimes_gen(zfilter (ztl(nums), (x:Int) => x % zhd(nums) != 0 ) ) ) )

def zprimes = zprimes_gen(zcount_from(2))
