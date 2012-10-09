abstract class ZTree[+T] 
case object ZNil extends ZTree[Nothing] 
case class ZNode[T](hd: T, left: Unit=>ZTree[T], right: Unit=>ZTree[T]) extends ZTree[T] 


def lz[T](l: =>ZTree[T]) = { lazy val v = l ; (u:Unit)=>v }

def lZNode[T](h: T, l: =>ZTree[T], r: =>ZTree[T]) = ZNode(h, lz(l), lz(r))

def root[T](t: ZTree[T]):T = 
{
  t match 
  { 
    case ZNil => error ("root: Root of Empty Tree")
    case ZNode(x,_,_) => x
  }
}

def left[T](t: ZTree[T]):ZTree[T] = 
{
  t match 
  {
    case ZNil => ZNil
    case ZNode(_,l,_) => l()
  }
}

def right[T](t: ZTree[T]):ZTree[T] = 
{
  t match 
  {
    case ZNil => ZNil
    case ZNode(_,_,r) => r()
  }
}

def map[T](t: ZTree[T], f: T=>T): ZTree[T] = 
{
  t match 
  {
    case ZNil => ZNil
    case ZNode(x, l, r) => lZNode(f(x), map(l(),f), map(r(),f))
  }
}

def find[T](t: ZTree[T], f: T => Boolean):Option[T] = 
{
  t match 
  {
    case ZNil => None
    case ZNode(x, l, r) => lazy val b =  find(l(),f); if ( f(x) ) Some(x) else  if ( b == None ) find(r(), f) else b
  }
}

def power2_calc(x:Int, acc:Int):Int = if( x == 0 ) acc else power2_calc(x-1,acc*2)
def pow2(x:Int) = power2_calc(x,1)

val t123 = lZNode(1, lZNode(2, ZNil, ZNil), lZNode(3, ZNil, ZNil))
val t0:ZNode[Int] = lZNode(0, t0:ZNode[Int], t0:ZNode[Int])
val t10:ZNode[Int] = lZNode(0, lZNode(1, t10, t10), lZNode(0, t10, t10))

def tints_gen (x:Int, n:Int):ZNode[Int] = 
{
  lazy val a = x*2;
  lZNode(x,tints_gen(a+1, n + 1), tints_gen(a+2, n + 1))
}
val ints = tints_gen(0,0)


def take[T](h:Int, t: ZTree[T]):ZTree[T] = 
{
  t match 
  {
    case ZNil => ZNil
    case ZNode(x, l, r) => if( h < 0) ZNil else lZNode(x, take(h-1, l()), take(h-1,r()))
  }
}
