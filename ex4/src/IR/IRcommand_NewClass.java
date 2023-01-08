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

public class IRcommand_NewClass extends IRcommand
{
	String className;
	TEMP result;
	
	public IRcommand_NewClass(TEMP result, String className)
	{
		this.result = result;
		this.className = className;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//missing
	}
}
