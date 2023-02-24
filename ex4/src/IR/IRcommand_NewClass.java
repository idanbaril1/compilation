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
import TEMP.*;
import MIPS.*;
import java.io.PrintWriter;

public class IRcommand_NewClass extends IRcommand
{
	String className;
	int classSize;
	int realSize;
	TEMP result;
	TYPE_CLASS father;
	public IRcommand_NewClass(TEMP result, String className, int size, int realSize, TYPE_CLASS father)
	{
		this.result = result;
		this.className = className;
		this.classSize = size;
		this.realSize = realSize;
		this.father = father;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		int residx = result.getSerialNumber()%10;
		MIPSGenerator.getInstance().li("$v0", 9);
		MIPSGenerator.getInstance().li("$a0", classSize);
		MIPSGenerator.getInstance().syscall();
		MIPSGenerator.getInstance().move("$a0", "$v0");

		MIPSGenerator.getInstance().li("$s1", 1);
		MIPSGenerator.getInstance().store("$s1", 0,"$a0");
		MIPSGenerator.getInstance().jal("class_" + className);
		if(father!=null){
			realSize = realSize - 4;
			MIPSGenerator.getInstance().move("$a2", "$a0");
			MIPSGenerator.getInstance().addi("$a0", "$a2", realSize);
			MIPSGenerator.getInstance().jal("class_" + father.name);
		}

		MIPSGenerator.getInstance().move(String.format("$t%d", residx), "$a2");
	}
}
