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

public class IRcommand_Init_Field extends IRcommand
{
	int fieldOffset;
	int intVal;
	String strVal;
	
	public IRcommand_Init_Field(int fieldOffset, int intVal, String strVal)
	{
		this.fieldOffset = fieldOffset;
		this.intVal = intVal;
		this.strVal = strVal;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (intVal != 0){
			MIPSGenerator.getInstance().li("$s0", intVal);
			MIPSGenerator.getInstance().store("$s0", fieldOffset, "$a0");
		}
		else{
			String constvar = String.format("str_const_%s", strVal);
			MIPSGenerator.getInstance().loadString(constvar, strVal);
			MIPSGenerator.getInstance().la("$s0", constvar);
			MIPSGenerator.getInstance().store("$s0", fieldOffset, "$a0");
		}
	}
}
