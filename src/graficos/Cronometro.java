package graficos;

public class Cronometro extends Thread {

	private boolean rodando;
	private long tempoInicial;
	private long tempoFinal;
	
	public void run(){
		tempoInicial = System.currentTimeMillis();
		rodando = true;
		
		while (rodando) {
			tempoFinal  = System.currentTimeMillis() - tempoInicial;
		}
		
	}
	
	public void parar() {
		rodando = false;
	}
	
	public long getTempo() {
		
		return tempoFinal;
		
	}
	
	
}
