package AST;

public class AST_STMT_RETURN extends AST_STMT
{
	/***************/
	/* return [exp]; */
	/***************/
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp)
	{
		this.exp = exp;
	}
}