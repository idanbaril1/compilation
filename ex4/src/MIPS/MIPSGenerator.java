/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.format("program_end:\n");
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tmove $a0,$t%d\n",idx%10);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");

		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	public void print_string(TEMP stringAddress){
		int idx=stringAddress.getSerialNumber();
		fileWriter.format("\tmove $a0,$t%d\n",idx%10);
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi $t%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void allocate(String var_name, String val)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word %s\n",var_name, val);
		fileWriter.format(".text\n");
	}
	public void allocateBuffer(String var_name, int size)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\t%s: .space %d\n",var_name, size);
		fileWriter.format(".text\n");
	}
	public void load(TEMP dst,String var_name)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw $t%d, global_%s\n",idxdst%10,var_name);
	}
	public void store(String var_name,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw $t%d,global_%s\n",idxsrc%10,var_name);		
	}
	public void store(String src,int index, String base)
	{
		fileWriter.format("\tsw %s, %d(%s)\n",src,index,base);
	}
	public void sw(TEMP src,TEMP base, int offset){
		int idxsrc=src.getSerialNumber();
		int idxbase=base.getSerialNumber();
		fileWriter.format("\tsw $t%d,%d($t%d)\n",idxsrc%10,offset,idxbase%10);	
	}
	public void lw(TEMP dst,TEMP base, int offset){
		int idxdst=dst.getSerialNumber();
		int idxbase=base.getSerialNumber();
		fileWriter.format("\tlw $t%d,%d($t%d)\n",idxdst%10,offset,idxbase%10);	
	}
	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli $t%d,%d\n",idx%10,value);
	}
	public void lb(String dst, int offset, String base)
	{
		fileWriter.format("\tlb %s,%d(%s)\n",dst,offset,base);
	}
	public void sb(String src, int offset, String base)
	{
		fileWriter.format("\tsb %s,%d(%s)\n",src,offset,base);
	}
	public void la(String dst, String var)
	{
		fileWriter.format("\tla %s,%s\n",dst,var);
	}
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd $t%d,$t%d,$t%d\n",dstidx%10,i1%10,i2%10);
	}
	public void add(String dst, String oprnd1,String oprnd2)
	{
		fileWriter.format("\tadd %s,%s,%s\n",dst,oprnd1,oprnd2);
	}
	public void mul(String dst, String oprnd1,String oprnd2)
	{
		fileWriter.format("\tmul %s,%s,%s\n",dst,oprnd1,oprnd2);
	}
	public void muli(String dst, String oprnd1,int imm)
	{
		fileWriter.format("\tmul %s,%s,%d\n",dst,oprnd1,imm);
	}
	public void xor(String dst, String oprnd1,String oprnd2)
	{
		fileWriter.format("\txor %s,%s,%s\n",dst,oprnd1,oprnd2);
	}
	public void and(String dst, String oprnd1,String oprnd2)
	{
		fileWriter.format("\tand %s,%s,%s\n",dst,oprnd1,oprnd2);
	}
	public void move(String dst, String src)
	{
		fileWriter.format("\tmove %s,%s\n",dst,src);
	}
	public void syscall()
	{
		fileWriter.format("\tsyscall\n");
	}
	public void li(String dst, int imm)
	{
		fileWriter.format("\tli %s, %d\n",dst,imm);
	}
	public void addi(TEMP dst,TEMP oprnd,int imm)
	{
		int i =oprnd.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\taddi $t%d,$t%d,%d\n",dstidx%10,i%10,imm);
	}
	public void addi(String dst,String oprnd,int imm)
	{
		fileWriter.format("\taddi %s,%s,%d\n",dst,oprnd,imm);
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub $t%d,$t%d,$t%d\n",dstidx%10,i1%10,i2%10);
	}
	public void subu(TEMP dst,TEMP oprnd1,int imm)
	{
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsubu $t%d,$t%d,%d\n",dstidx%10,i1%10,imm);
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul $t%d,$t%d,$t%d\n",dstidx%10,i1%10,i2%10);
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv $t%d,$t%d,$t%d\n",dstidx%10,i1%10,i2%10);
	}
	public void loadReturnReg(TEMP src){
		if(src!=null){
			int idxsrc=src.getSerialNumber();
			fileWriter.format("\tmove $v0, $t%d\n",idxsrc%10);
		}	
	}
	public void loadFuncReturnValue(TEMP dst){
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tmove $t%d, $v0\n",idxdst%10);
	}
	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format(".text\n");
			fileWriter.format("%s:\n",inlabel);
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);
		}
	}	
	public void jump(String inlabel)
	{
		fileWriter.format(".text\n");
		fileWriter.format("\tj %s\n",inlabel);
	}
	public void jal(String inlabel)
	{
		fileWriter.format("\tjal %s\n",inlabel);
	}	
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt $t%d,$t%d,%s\n",i1%10,i2%10,label);				
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge $t%d,$t%d,%s\n",i1%10,i2%10,label);				
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne $t%d,$t%d,%s\n",i1%10,i2%10,label);				
	}
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq $t%d,$t%d,%s\n",i1%10,i2%10,label);				
	}
	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq $t%d,$zero,%s\n",i1%10,label);				
	}
	public void beqz(String oprnd1,String label)
	{
		fileWriter.format("\tbeq %s,$zero,%s\n",oprnd1,label);
	}
	public void bnez(String oprnd1,String label)
	{
		fileWriter.format("\tbne %s,$zero,%s\n",oprnd1,label);
	}
	public void bne(String oprnd1, String oprnd2, String label)
	{
		fileWriter.format("\tbne %s,%s,%s\n",oprnd1,oprnd2,label);
	}
	public void beq(String oprnd1, String oprnd2, String label)
	{
		fileWriter.format("\tbeq %s,%s,%s\n",oprnd1,oprnd2,label);
	}
	public void blt(String oprnd1, String oprnd2, String label)
	{
		fileWriter.format("\tblt %s,%s,%s\n",oprnd1,oprnd2,label);
	}
	public void bltz(String oprnd1, String label)
	{
		fileWriter.format("\tbltz %s,%s\n",oprnd1,label);
	}
	public void blti(String oprnd1,int imm, String label)
	{
		fileWriter.format("\tblti %s,%d,%s\n",oprnd1,imm,label);
	}

	public void funcStart(){
		fileWriter.format("\tsubu $sp, $sp,4\n");
		fileWriter.format("\tsw $ra, 0($sp)\n");
		fileWriter.format("\tsubu $sp, $sp,4\n");
		fileWriter.format("\tsw $fp, 0($sp)\n");
		fileWriter.format("\tmove $fp, $sp\n");
		fileWriter.format("\tsubu $sp, $sp,16\n");
	}
	public void loadArg(String argName){
		/* Store value of argname in a temp to not override a var from outer scope */
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		load(t,argName);
		String tempName = argName + "_temp";
		allocate(tempName, "721");
		store(tempName, t);
		fileWriter.format("\tlw $t0, 0($sp)\n");
		fileWriter.format("\taddu $sp, $sp, 4\n");
		fileWriter.format("\tsw $t0, global_%s\n", argName);
	}
	public void restoreArg(String argName){
		String tempName = argName + "_temp";
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		load(t,tempName);
		store(argName,t);
	}
	public void funcEnd(){
		fileWriter.format("\tmove $sp, $fp\n");
		fileWriter.format("\tlw $fp, 0($sp)\n");
		fileWriter.format("\tlw $ra, 4($sp)\n");
		fileWriter.format("\taddu $sp, $sp, 8\n");
		fileWriter.format("\tjr $ra\n");
	}
	public void storeArg(TEMP value){
		int index = value.getSerialNumber();
		fileWriter.format("\tsubu $sp, $sp, 4\n");
		fileWriter.format("\tsw $t%d, 0($sp)\n", index%10);
	}
	public void loadString(String name, String value){
		fileWriter.format(".data\n");
		fileWriter.format("\t%s: .asciiz \"%s\"\n", name, value);
		fileWriter.format(".text\n");
	}
	public void arrayAccess(TEMP result, TEMP arrBase, TEMP place){
		int resIdx = result.getSerialNumber();
		int baseIdx = arrBase.getSerialNumber();
		int placeIdx = place.getSerialNumber();
		fileWriter.format("bltz $t%d, program_end\n", placeIdx%10);
		fileWriter.format("lw $s0, 0($t%d)\n", baseIdx%10);
		fileWriter.format("bge $t%d, $s0, program_end\n", placeIdx%10);

		fileWriter.format("move $s0, $t%d\n", placeIdx%10);
		fileWriter.format("add $s0, $s0, 1\n");
		fileWriter.format("mul $s0, $s0, 4\n");
		fileWriter.format("addu $s0, $t%d, $s0\n", baseIdx%10);
		fileWriter.format("lw $t%d, 0($s0)\n", resIdx%10);
	}
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static MIPSGenerator instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected MIPSGenerator() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new MIPSGenerator();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./output/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		return instance;
	}
}
