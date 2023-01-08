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

public class IRcommand_FieldSet extends IRcommand
{
	String fieldName;
	TEMP value;
	TEMP obj;
	
	public IRcommand_FieldSet(TEMP obj, String fieldName, TEMP value)
	{
		this.value = value;
		this.fieldName = fieldName;
		this.obj = obj;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//missing
	}
}
