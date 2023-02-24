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
	int fieldOffset;
	TEMP value;
	TEMP obj;
	
	public IRcommand_FieldSet(TEMP obj, int fieldOffset, TEMP value)
	{
		this.value = value;
		this.fieldOffset = fieldOffset;
		this.obj = obj;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int validx = value.getSerialNumber()%10;
		int objidx = obj.getSerialNumber()%10;
		MIPSGenerator.getInstance().beqz(String.format("$t%d", objidx), "invalid_pointer_label");

		MIPSGenerator.getInstance().store(String.format("$t%d", validx), fieldOffset, String.format("$t%d", objidx));
	}
}
