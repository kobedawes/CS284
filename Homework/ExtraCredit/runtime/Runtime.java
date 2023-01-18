package runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
// import java.util.HashMap;
// import java.util.Arrays;

public class Runtime {
	// data fields
	private ArrayList<Instruction> pgm;
	private int pc;
	private Stack<Double> stack;
	private ArrayList<Double> memory;
	private boolean jump = false;
	private int jumpTarget;
	private boolean repeat = false;
	private int repeatLim;
	Runtime() {
		stack = new Stack<>();
		memory = new ArrayList<>(10);	
	}
	
	/**
	 * Set SBM to initial state
	 */
	private void initialize() {
		// empty stack
		while (!stack.isEmpty()) {
			stack.pop();
		}
		// reset all memory locations
		for (int i=0; i<10; i++) {
			memory.add(0.0);
		}
		// set pc to first instruction
		pc=1;
	}
	
    private int jumpToLabel(String label) {
	 // implement
		int i = 0;
		for(Instruction ins: pgm){
			if(ins.toString().equals("label " + label)){
				return i;
			}
			i++;
		}
		throw new IllegalArgumentException("runtime: label not found");
	}
	
	private boolean processInstruction(Instruction i) {
	    // implement

		switch(i.code){
			case 0: // exit 
				if(!repeat){
					pc+=1;
				}
				return false;
			case 1: // push literal
				if(!repeat){
					pc+=1;
				}
				Double lit = Double.parseDouble(i.toString().split(" ")[1]);
				stack.add(lit);
				break;
			case 2: // push memory i.e. m3
				if(!repeat){
					pc+=1;
				}
				int address = Integer.parseInt(i.toString().split(" ")[1].substring(1));
				stack.add(memory.get(address));
				break;
			case 3: // pop 
				if(!repeat){
					pc+=1;
				}
				int popAddress = Integer.parseInt(i.toString().split(" ")[1].substring(1));
				memory.set(popAddress, stack.pop());
				break;
			case 4: // add
				if(!repeat){
					pc+=1;
				}
				Double addFirst = stack.pop();
				Double addSecond = stack.pop();
				stack.add(addFirst+addSecond);
				break;
			case 5: // subtract second from first
				if(!repeat){
					pc+=1;
				}
				Double subFirst = stack.pop();
				Double subSecond = stack.pop();
				stack.add(subFirst-subSecond);
				break;
			case 6: // multiply
				if(!repeat){
					pc+=1;
				}
				Double mulFirst = stack.pop();
				Double mulSecond = stack.pop();
				stack.add(mulFirst*mulSecond);
				break;
			case 7: // divide first by second
				if(!repeat){
					pc+=1;
				}
				Double divFirst = stack.pop();
				Double divSecond = stack.pop();
				stack.add(divFirst/divSecond);
				break;
			case 8: // label
				if(!repeat){
					pc+=1;
				}
				break;
			case 9: // jmpz
				if(!repeat){
					pc+=1;
				}
				String jzTarget = i.toString().split(" ")[1];
				if(stack.peek() == 0){
					jump = true;
					jumpTarget = jumpToLabel(jzTarget);
				}
				break;
			case 10: // jump
				if(!repeat){
					pc+=1;
				}
				String jTarget = i.toString().split(" ")[1];
				jump = true;
				jumpTarget = jumpToLabel(jTarget);
				break;
			case 11: // decrement
				if(!repeat){
					pc+=1;
				}
				stack.setElementAt(stack.peek()-1, stack.size()-1);
				break;
		}
		return true;
	}
	
	public void run() {
		boolean run = true;
		initialize();
		while(run){
			for(int i=0; i<pgm.size(); i++){
				// if need be check code of pgm[i] if jump class jump to label, and set current pc to variable until its passed
				// or set pc to the instruction+1
				if(jump){
					jump = false;
					repeatLim = i;
					i = jumpTarget;
					if(jumpTarget < repeatLim){
						repeat = true;
					}
				}
				if(i>repeatLim){
					repeat = false;
				}
				run = processInstruction(pgm.get(i));
			}
		}
		return;
	}
	
	private Instruction parseInstruction(String str, int line) {
		String[] p = str.split("[ ]+"); // delimiters are non-empty sequences of spaces
		Instruction i=null;

		switch (p[0]) {
		case "exit":
			i = new Exit(0,"exit");
			break;
		case "push": 
			if (p.length==1) {
				throw new IllegalStateException("parseInstruction: syntax error at line "+line);
			}
			try {
			if (p[1].charAt(0)=='m') {
				int loc = Integer.parseInt(p[1].substring(1));
				if (loc<0 || loc>9) {
					throw new IllegalStateException("parseInstruction: syntax error at line "+line);
				}
				i = new PushLocation(2,"push",loc);
			} else {
				i = new PushLiteral(1,"push",Double.parseDouble(p[1]));
			}
			} catch (NumberFormatException e) {
				throw new IllegalStateException("parseInstruction: syntax error at line "+line);
			}
			break;
		case "pop": 
			if (p.length==1) {
				throw new IllegalStateException("parseInstruction: syntax error at line "+line);
			}
			int loc = Integer.parseInt(p[1].substring(1));
			if (loc<0 || loc>9) {
				throw new IllegalStateException("parseInstruction: syntax error at line "+line);
			}
			i = new Pop(3,"pop",loc);
			break;
		case "add": 
			i = new Add(4,"add");
			break;
		case "sub": 
			i = new Sub(5,"sub");
			break;
		case "mul": 
			i = new Mul(6,"mul");
			break;
		case "div": 
			i = new Div(7,"div");
			break;
		case "label":
			i = new Label(8,"label",p[1]);
			break;
		case "jmpz":
			i = new Jmpz(9,"jmpz",p[1]);
			break;
		case "jmp":
			i = new Jmp(10,"jmp",p[1]);
			break;
		case "dec":
			i = new Dec(11,"dec");
			break;
		case "":
			break;
		default:
			throw new IllegalStateException("parseInstruction: syntax error at line "+line);
		}
		return i;
	}
	
	public void readFromFile(String name)  {
		pgm = new ArrayList<>();
		File f = new File(name);
		try {
			Scanner s = new Scanner(f);
			int line=1;
			
			while (s.hasNext()) {
				Instruction i = parseInstruction(s.nextLine(),line);
				if (i!=null) {
					pgm.add(i);
				}
				line++;
			}
			s.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Pgm   : ");
		r.append(pgm==null?"null":pgm.toString());
		r.append("\nPc    : "+pc);
		r.append("\nStack : ");
		r.append(stack.toString());
		r.append("\nMemory: ");
		r.append(memory.toString());
		r.append("\n------------------------------------------------\n");
		
		return r.toString();
	}
	
	public static void main(String[] args) {
		Runtime r = new Runtime();
		r.readFromFile("eg4.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
		System.out.println(r);  // print resulting state of the SBM

	}	
}
