package Main;

public class test {
    private Type type;

    test(Type type){
    	this.type = type;
    }
    public Type getType() { return type; }
	public void setType(Type type) { this.type = type; }
    public static void swap(test cell1, test cell2) {
        Type temp = cell1.getType();
        cell1.setType(cell2.getType());
        cell2.setType(temp);
    }
    public void print() {
    	System.out.println(this.type);
    }
    
    public static void main(String[] args) {
    	test cell1 = new test(Type.GHOST);
    	test cell2 = new test(Type.DARK_FROG);
    	
    	cell1.print();
    	cell2.print();
    	
    	test.swap(cell1, cell2);
    	
    	cell1.print();
    	cell2.print();
    }
}
