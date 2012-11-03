public class Pair<S,T> {
  public S o1;
  public T o2;
  public Pair(S o1, T o2) { this.o1 = o1; this.o2 = o2; }
 
  public static boolean same(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
  }
 
  S getFirst() { return o1; }
  T getSecond() { return o2; }
 
  void setFirst(S o) { o1 = o; }
  void setSecond(T o) { o2 = o; }
 
  public boolean equals(Object obj) {
    if( ! (obj instanceof Pair))
      return false;
    Pair<S,T> p = (Pair<S,T>)obj;
    return same(p.o1, this.o1) && same(p.o2, this.o2);
  }
 
  public String toString() {
    return "Pair{"+o1+", "+o2+"}";
  }

 
}