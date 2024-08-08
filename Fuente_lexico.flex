package fes.aragon.compilador;
import java_cup.runtime.Symbol;
import java.io.Reader;
%%
%{
	public int getYyline() {
                return yyline;
        }

        public int getYy_currentPos() {
            return yy_currentPos-1;
        }
%}
%class Lexico
%public
%char
%line
%cup
%ignorecase
%type java_cup.runtime.Symbol
//%implements java_cup.runtime.Scanner
%eofval{
 return new Symbol(sym.EOF,new String("Fin del archivo"));
//return null;
%eofval}
DIGITO=[0-9]
INT={DIGITO}+
%%
"coloca" {System.out.println("coloca"); return new Symbol(sym.COLOCA); }
"mover" {System.out.println("mover"); return new Symbol(sym.MOVER); }
"arriba" {System.out.println("arriba"); return new Symbol(sym.ARRIBA); }
"abajo" {System.out.println("abajo"); return new Symbol(sym.ABAJO); }
"izquierda" {System.out.println("izquierda"); return new Symbol(sym.IZQUIERDA); }
"derecha" {System.out.println("derecha"); return new Symbol(sym.DERECHA); }
"repetir" {System.out.println("repetir"); return new Symbol(sym.REPETIR); }
"{" {System.out.println("llaveAbre"); return new Symbol(sym.LLLAVE); }
"}" {System.out.println("llaveCierra"); return new Symbol(sym.RLLAVE); }
";" {System.out.println("puntoycoma"); return new Symbol(sym.PUNTOYCOMA); }
"ver fruta" {System.out.println("verfruta"); return new Symbol(sym.VFRUTA); }
" " {System.out.println("espacio"); return new Symbol(sym.ESPACIO); }


{INT}+ {
		System.out.println("Entero");
		return new Symbol(sym.NUMERO, new Integer(yytext())); }
[\t\r\f]  {}
[\n] {}
" " {System.out.println("Simbolo ."+yytext());}
. { System.out.println("Caracter no valido. "+yytext()+"-"); }


