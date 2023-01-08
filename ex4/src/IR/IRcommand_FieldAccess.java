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

public class IRcommand_FieldAccess extends IRcommand
{
	String field;
	TEMP classObj;
	TEMP result;
	
	public IRcommand_FieldAccess(TEMP result, TEMP classObj, String field)
	{
		this.result = result;
		this.classObj = classObj;
		this.field = field;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//missing
	}
}
