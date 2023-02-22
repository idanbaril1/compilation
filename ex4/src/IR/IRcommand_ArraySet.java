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
	TEMP place;
	TEMP value;
	TEMP arrBase;
	
	public IRcommand_ArraySet(TEMP arrBase, TEMP place, TEMP value)
	{


		this.value = value;
		this.arrBase = arrBase;
		this.place = place;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int valIdx = value.getSerialNumber() % 10;
		int placeIdx = place.getSerialNumber() % 10;
		int baseIdx = arrBase.getSerialNumber() % 10;
		MIPSGenerator.getInstance().bltz(String.format("$t%d", placeIdx%10), "arrayAccessError_label");
		MIPSGenerator.getInstance().lw("$s0", String.format("$t%d",baseIdx%10), 0);
		MIPSGenerator.getInstance().bge(String.format("$t%d", placeIdx%10), "$s0", "arrayAccessError_label");

		MIPSGenerator.getInstance().move("$s0", String.format("$t%d", placeIdx));
		MIPSGenerator.getInstance().addi("$s0", "$s0", 1);
		MIPSGenerator.getInstance().muli("$s0", "$s0", 4);
		MIPSGenerator.getInstance().add("$s0", String.format("$t%d", baseIdx), "$s0");
		MIPSGenerator.getInstance().store(String.format("$t%d", valIdx), 0, "$s0");
	}
}
