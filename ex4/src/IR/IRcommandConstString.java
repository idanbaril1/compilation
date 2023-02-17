/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import MIPS.*;

public class IRcommandConstString extends IRcommand
{
	TEMP t;
	String name;
	String value;
	
	public IRcommandConstString(String name,String value)
	{
		this.name = name;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGenerator.getInstance().loadString(name,value);
	}
}
