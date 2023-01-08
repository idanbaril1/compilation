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

public class IRcommand_LoadArg extends IRcommand
{
	String name;
	int index;
	
	public IRcommand_LoadArg(String name, int index)
	{
		this.name = name;
		this.index = index;
	}
	
	
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//missing
	}
}
