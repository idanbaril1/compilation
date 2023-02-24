package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;

	public int classSize;
	public int realClassSize;
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
		this.classSize = 4;
		this.realClassSize = 4;
		setClassSize();
	}
	public boolean isClass(){ return true;}
	
	public TYPE findMethodInClass(String name){
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_FUNCTION method = null;
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_FUNCTION){
				method = (TYPE_FUNCTION)member;
				if(method.name.equals(name)){
					return method;
				}
			}
			members = members.tail;
		}
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_FUNCTION){
					method = (TYPE_FUNCTION)member;
					if(method.name.equals(name)){
						return method;
					}
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
		return null;
	}
	
	public TYPE findFieldInClass(String name){
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_CLASS_VAR_DEC field = null;
		
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_CLASS_VAR_DEC){
				field = (TYPE_CLASS_VAR_DEC)member;
				if(field.name.equals(name)){
					return field.t;
				}
			}
			members = members.tail;
		}
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_CLASS_VAR_DEC){
					field = (TYPE_CLASS_VAR_DEC)member;
					if(field.name.equals(name)){
						return field.t;
					}
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
		
		return null;
	}

	private void setClassSize(){
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_CLASS_VAR_DEC field = null;
		
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_CLASS_VAR_DEC){
				classSize = classSize + 4;
			}
			members = members.tail;
		}
		realClassSize = classSize;
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_CLASS_VAR_DEC){
					classSize = classSize + 4;
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
	}
	public int getFieldOffset(String name){
		int offset = 4;
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_CLASS_VAR_DEC field = null;
		
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_CLASS_VAR_DEC){
				field = (TYPE_CLASS_VAR_DEC)member;
				if(field.name.equals(name)){
					return offset;
				}
				offset = offset + 4;
			}
			members = members.tail;
		}
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_CLASS_VAR_DEC){
					field = (TYPE_CLASS_VAR_DEC)member;
					if(field.name.equals(name)){
						return offset;
					}
					offset = offset + 4;
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
		
		return 0;
	}
	public int getMethodOffset(String name){
		int offset = 4;
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_FUNCTION method = null;
		
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_FUNCTION){
				method = (TYPE_FUNCTION)member;
				if(method.name.equals(name)){
					return offset;
				}
				offset = offset + 4;
			}
			members = members.tail;
		}
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_FUNCTION){
					method = (TYPE_FUNCTION)member;
					if(method.name.equals(name)){
						return offset;
					}
					offset = offset + 4;
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
		
		return 0;
	}
	public String getFieldNameByOffset(int offset){
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_CLASS_VAR_DEC field = null;
		
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_CLASS_VAR_DEC){
				offset = offset - 4;				
				if(offset == 0){
					field = (TYPE_CLASS_VAR_DEC)member;
					return field.name;
				}
				
			}
			members = members.tail;
		}
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_CLASS_VAR_DEC){
					offset = offset - 4;				
					if(offset == 0){
						field = (TYPE_CLASS_VAR_DEC)member;
						return field.name;
					}
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
		
		return null;
	}
	public String getMethodRealClassName(String methodName){
		TYPE_LIST members = data_members;
		TYPE member = null;
		TYPE_FUNCTION method = null;
		while(members!=null){
			member = members.head;
			if(member instanceof TYPE_FUNCTION){
				method = (TYPE_FUNCTION)member;
				if(method.name.equals(methodName)){
					return name;
				}
			}
			members = members.tail;
		}
		/*search in fathers classes */
		TYPE_CLASS ancestor = father;
		while (ancestor!=null){
			members = ancestor.data_members;
			while(members!=null){
				member = members.head;
				if(member instanceof TYPE_FUNCTION){
					method = (TYPE_FUNCTION)member;
					if(method.name.equals(methodName)){
						return ancestor.name;
					}
				}
				members = members.tail;
			}
			ancestor = ancestor.father;
		}
		return null;
	}

}
