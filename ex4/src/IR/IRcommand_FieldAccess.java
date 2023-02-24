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
	int fieldOffset;
	TEMP classObj;
	TEMP result;
	
	public IRcommand_FieldAccess(TEMP result, TEMP classObj, int fieldOffset)
	{
		this.result = result;
		this.classObj = classObj;
		this.fieldOffset = fieldOffset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int residx = result.getSerialNumber()%10;
		int objidx = classObj.getSerialNumber()%10;
		MIPSGenerator.getInstance().beqz(String.format("$t%d", objidx), "invalid_pointer_label");

		MIPSGenerator.getInstance().lw(String.format("$t%d", residx), String.format("$t%d", objidx), fieldOffset);
	}
}
