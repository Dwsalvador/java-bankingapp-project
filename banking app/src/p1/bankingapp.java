package p1;
import java.time.LocalDate; /*fonction pour recuperer date sys*/
import java.util.Arrays;
import java.util.Scanner;
class date {
	private int anne;
	private int moin;
	private int jour;
	public date(int anne,int moin,int jour){
		if(anne<0) {
			System.out.print("l'année doit être positive");
			System.exit(0);
		}
		else if(moin<1 || moin>12) {
			System.out.print("le mois est invalide");
			System.exit(0);
		}
		else if( (moin == 1 || moin == 3 || moin == 5 || moin == 7 || moin == 8 || moin == 10 || moin == 12)&& jour>31) {
		    System.out.println("jour invalide");
		    System.exit(0);
		} 
		else if ((moin == 4 || moin == 6 || moin == 9 || moin == 11)&& jour>30) {
		    System.out.println("jour invalide");
		    System.exit(0);
		}
		else if (jour<=0) {
			System.out.print("le jour doit être positif");
			System.exit(0);
		}
		
		else if (anne%4==0 && moin==2 && jour>29) {
			System.out.print("date invalide");
			System.exit(0);
		}
		else if (anne%4 !=0 && moin==2 && jour>28) {
			System.out.print("date invalide");
			System.exit(0);
		}
		else {
			this.anne=anne;
			this.moin=moin;
			this.jour=jour;
		}
		
	}
	public int getanne() {
		return this.anne;
	}
	public int getmoin() {
		return this.moin;
	}
	public int getjour() {
		return this.jour;
	}
	public String toString() {
		return this.jour+"/"+this.moin+"/"+this.anne;
		
	}
}
class transaction{
	float amount;
	date creation_date;
	transaction(float amount,date creation_date){
		this.amount=amount;
		this.creation_date=creation_date;
	}
	public String toString() {
		return "montant="+amount+",date creation "+creation_date;
	}
}
class account{
	private float balance;
	static int c=0;
	private transaction[] previous_transactions;
	private int i=0;
	private String name;
	private String id;
	public account(float balance,String name){
		this.balance=balance;
		this.name=name;
		this.id= String.format("%s%04d", name.substring(0, 2).toUpperCase(), c);/*id sous forme "MM0001"*/
		c++;
		previous_transactions=new transaction[10];
		System.out.println("compte créé avec succès");
	}
	public void history() {
		for(int j=0;j<i;j++) {
			System.out.println(j+1+"."+previous_transactions[j]);
		}
	}
	public String getname() {
		return this.name;
	}
	public String getid() {
		return this.id;
	}
	public String toString() {
		return name+" "+id+" "+balance;
	}
	public float getbalance() {
		return this.balance;
	}
	public void deposit(float amount){
		if(amount==0) {
			System.out.println("montant invalide");
		}
		else{
			balance=balance+amount;
			LocalDate currentDate = LocalDate.now();
			int day = currentDate.getDayOfMonth();
	        int month = currentDate.getMonthValue();
	        int year = currentDate.getYear();
	        if(i>=previous_transactions.length){
	        	previous_transactions=Arrays.copyOf(previous_transactions,i-previous_transactions.length+1);
	        }
				previous_transactions[i]=new transaction(amount,new date(year,month,day));
				i=i+1;
	        }
			System.out.println("opération réussie");
		}
	public void withdrawal(float amount){
		if(amount>balance) {
			System.out.println("le compte n'a pas ce montant");
		}
		else {
			
		
		deposit(amount*-1);}
	}
	}
class bank{
	private int i=0;
	private account[] accounts;
	private String name;
	bank(String name){
		this.name=name;
		this.accounts=new account[10];
	}
	public void add_account(account acc) {
		 if(i>=accounts.length){
			 accounts=Arrays.copyOf(accounts,accounts.length+1);
	       }
		 accounts[i]=acc;
		 i++;
	}
	public account find_account(String id) {
		for(int j=0;j<i;j++){
			if(accounts[j].getid().equals(id)) {
				System.out.println("connexion réussie");
				return accounts[j];
			}
		}
		System.out.println("compte introuvable");
		return null;
	}
	public String getname() {
		return name;
	}
	public account[] getaccounts() {
		return accounts;
	}
}
public class bankingapp {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		bank b=new bank("BIAT");
		int n=0;
		
		do {
			System.out.println("choisir une opération");
			System.out.println("1-créer un compte");
			System.out.println("2-se connecter");
			System.out.println("3-quitter");
			n=sc.nextInt();
			while(n>3 || n<=0){
				System.out.println("choisir une opération entre 1 et 3");
				n=sc.nextInt();}
			
			switch(n) {
				case 1:
					System.out.println("insérer votre nom:");
					String name=sc.next();
					float bal=-1;
					do {
						System.out.println("insérer votre dépôt initial");
						bal=sc.nextFloat();
					}while(bal<=0);
					
					account acc=new account(bal,name);
					b.add_account(acc);
					System.out.println("bienvenue,"+acc.getname()+" votre id="+acc.getid());
					break;
				case 2:
					System.out.println("donner votre id");
					String id=sc.next();
					account compt_cour=b.find_account(id);
					if(compt_cour==null) {
						break;
					}
					int op=0;
					do {
						System.out.println("choisir une opération");
						System.out.println("1-voir le solde");
						System.out.println("2-historique des opérations");
						System.out.println("3-faire un dépôt");
						System.out.println("4-faire un retrait");
						System.out.println("5-quitter");
						op=sc.nextInt();
						while(op>5 || op<=0){
							System.out.println("choisir une opération entre 1 et 5");
							op=sc.nextInt();
						};
						float amd=-1;
						switch(op) {
						
						case 1:
							System.out.println("solde="+compt_cour.getbalance());
							break;
						case 3:
							
							do {
								System.out.println("montant du dépôt:");
								amd=sc.nextFloat();
							}while(amd<0);
							compt_cour.deposit(amd);
						break;
						case 4:
							do {
								System.out.println("montant du retrait:");
								amd=sc.nextFloat();
							}while(amd<0);
							compt_cour.withdrawal(amd);
							break;
						case 2:
							System.out.println("votre historique:");
							compt_cour.history();
						}
					}while(op!=5);
					break;
			}
		
	}while(n!=3);
	sc.close();
}}