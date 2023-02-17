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

public class IRcommand_ArrayAccess extends IRcommand
{
	TEMP index;
	TEMP result;
	TEMP arrBase;
	public IRcommand_ArrayAccess(TEMP result, TEMP arrBase, TEMP index)
	{
		this.result = result;
		this.arrBase = arrBase;
		this.index = index;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme(){
		MIPSGenerator.getInstance().arrayAccess(result, arrBase, index);
	}
}
