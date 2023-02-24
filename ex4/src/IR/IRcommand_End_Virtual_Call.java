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
import TYPES.*;
import SYMBOL_TABLE.*;
import java.io.PrintWriter;
import TEMP.*;
import MIPS.*;

public class IRcommand_End_Virtual_Call extends IRcommand
{
	TEMP classObj;
	TYPE_CLASS classType;
	
	public IRcommand_End_Virtual_Call(TEMP classObj, TYPE_CLASS classType)
	{
		this.classObj = classObj;
		this.classType = classType;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		String fieldName;
		int objidx = classObj.getSerialNumber()%10;
		MIPSGenerator.getInstance().beqz(String.format("$t%d", objidx), "invalid_pointer_label");
		for(int i=4; i<classType.classSize; i=i+4){
			fieldName = classType.getFieldNameByOffset(i);
			MIPSGenerator.getInstance().loadFromVar("$s0", fieldName);
			MIPSGenerator.getInstance().store("$s0", i, String.format("$t%d",objidx));
		
			MIPSGenerator.getInstance().retrieveFieldName(fieldName);			
		}
		
	}
}
