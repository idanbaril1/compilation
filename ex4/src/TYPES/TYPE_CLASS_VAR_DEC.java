package TYPES;

public class TYPE_CLASS_VAR_DEC extends TYPE
{
	public TYPE t;
	public String name;
	public int intVal;
	public String strVal;
	
	public TYPE_CLASS_VAR_DEC(TYPE t,String name, int intVal, String strVal)
	{
		this.t = t;
		this.name = name;
		this.intVal = intVal;
		this.strVal = strVal;
	}
}
