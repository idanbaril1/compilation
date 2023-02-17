package AST;
import TYPES.*;
import TEMP.*;
import java.io.PrintWriter;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public int lineNumber;
	public PrintWriter fileWriter;
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	public TYPE SemantMe(){return null;};
	public TEMP IRme(){return null;};
	public TEMP IRme(String name){return null;};
}
