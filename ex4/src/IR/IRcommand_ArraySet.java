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

public class IRcommand_ArraySet extends IRcommand
{
	TEMP index;
	TEMP value;
	TEMP arrBase;
	
	public IRcommand_ArraySet(TEMP arrBase, TEMP index, TEMP value)
	{
		this.value = value;
		this.arrBase = arrBase;
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
