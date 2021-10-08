
public class ThreadProdutorConsumidor {

	public static volatile Object lock = new Object(); 
	public static volatile int produtos = 0;
	
	public static void main(String[] args) {
		Produtor p1 = new Produtor(1);
		p1.start();
		Consumidor c1 = new Consumidor(1);
		c1.start();
	}
}
class Produtor extends Thread {
	int id = 0;
	Produtor(int novoId) {
		this.id = novoId;
	}
	public void run() {
		for(int i = 0; i < 100; i ++ ) {	
			
		
				synchronized( ThreadProdutorConsumidor.lock )
				{	
					if(ThreadProdutorConsumidor.produtos < 100)
						ThreadProdutorConsumidor.produtos = ThreadProdutorConsumidor.produtos + 1;
					else {
						notify();
						try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("\nProdutor " + id + "; estoque = " + ThreadProdutorConsumidor.produtos);
				}	
		}
	}
}
class Consumidor extends Thread {
	int id = 0;
	Consumidor(int novoId) {
		this.id = novoId;
	}
	public void run() {
		for(int i = 0; i < 100; i++) {			
			synchronized( ThreadProdutorConsumidor.lock )
			{	
				
				if(ThreadProdutorConsumidor.produtos > 0)
					ThreadProdutorConsumidor.produtos = ThreadProdutorConsumidor.produtos - 1;
				else {
					notify();
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("\nConsumidor " + id + "; estoque = " + ThreadProdutorConsumidor.produtos);
			}
		}
	}

}
