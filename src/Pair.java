public class Pair {
  public String o1;
  public Integer o2;
  public Pair(String o1, Integer o2) { this.o1 = o1; this.o2 = o2; }
 
  public static boolean same(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
  }
 
  String getFirst() { return o1; }
  Integer getSecond() { return o2; }
 
  void setFirst(String o) { o1 = o; }
  void setSecond(int o) { o2 = o; }
 
  public boolean equals(Object obj) {
    if( ! (obj instanceof Pair))
      return false;
    Pair p = (Pair)obj;
    return same(p.o1, this.o1) && same(p.o2, this.o2);
  }
 
  public String toString() {
    return "Pair{"+o1+", "+o2+"}";
  }
 
}