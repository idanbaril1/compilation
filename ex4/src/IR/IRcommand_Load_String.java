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

public class IRcommand_Load_String extends IRcommand
{
    TEMP dst;
    String var_name;

    public IRcommand_Load_String(TEMP dst,String var_name)
    {
        this.dst      = dst;
        this.var_name = var_name;
    }

    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {
        int dstidx = dst.getSerialNumber() % 10;
        MIPSGenerator.getInstance().la(String.format("$t%d",dstidx),var_name);
    }
}
