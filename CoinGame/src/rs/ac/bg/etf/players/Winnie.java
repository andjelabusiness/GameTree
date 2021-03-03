package rs.ac.bg.etf.players;

import java.util.ArrayList;
import java.util.List;


public class Winnie extends Player {

	private Move myMove;
	private int brMogPoteza;
	private int taktika = 6;
	
	static int brMojih = 0;
	private boolean igramSaMojim;
	
	private List<Move> sviMojiPotezi;
	static int brPoteza;
	private boolean kradi = false;
	private boolean jaBrojim = false;
	
	
    public Winnie() {
        this.myMove = Move.PUT2COINS;
        brMogPoteza = 0;
        taktika = 6;
        igramSaMojim = false;
        this.sviMojiPotezi = new ArrayList<Move>();
        kradi = false;
        jaBrojim = false;
    }
    
    
    @Override
    public void resetPlayerState() {
        super.resetPlayerState();
        this.sviMojiPotezi.clear();
        this.myMove = Move.PUT2COINS;
        brMogPoteza = 0;
        taktika = 6;
        igramSaMojim = false;
        brMojih = 0;
        kradi = false;
        jaBrojim = false;
    }
	
	
	@Override
	public Move getNextMove() {
		
		if( brMogPoteza == 0) { // za proveru da li igram sa svojim
			brMojih++;
		}
		
		if (brMogPoteza == 0) {
			if (brPoteza!=0)   // znam koliko ima poteza
				kradi = true;
			else 
				jaBrojim = true; // ja moram da izbrojim
		}
		
		if (this.opponentMoves.size() > 0) {
			
			if (brMogPoteza == 1) {
				if (brMojih == 1) { // ne igram sa svojim
					brMojih = 0;
				}
				else if (brMojih == 2) { // igram sa mojim
					igramSaMojim = true;
				}
			}
			
			Move prviPotez = this.opponentMoves.get(0);
			Move oppMove = this.opponentMoves.get(this.opponentMoves.size() - 1); //1 iza potez
			Move oppMove2 = Move.DONTPUTCOINS; // 2 iza poteza
			
			if (this.opponentMoves.size()>=2) {
				oppMove2 = this.opponentMoves.get(this.opponentMoves.size() - 2);
			}
			
			if(brMogPoteza >=2 && oppMove == Move.DONTPUTCOINS && oppMove2 == Move.DONTPUTCOINS) {
				this.myMove = Move.DONTPUTCOINS;
			}
			
			else if( prviPotez == Move.DONTPUTCOINS && igramSaMojim ==false) { //stinger
				this.myMove = Move.DONTPUTCOINS;
			}
			else {
				
				if (igramSaMojim == true) { // 2 moja
					this.myMove = Move.PUT2COINS;
					taktika = 22;
				}
				
				else if (prviPotez == Move.PUT2COINS) { //goody/avenger
					if ( brMogPoteza == 1 || brMogPoteza==2) {
						this.myMove = Move.PUT1COIN;
						
						if (kradi == true && brMogPoteza == (brPoteza - 1)) {
							this.myMove = Move.DONTPUTCOINS;
						}
					}
					else { // potez dva i dalje
						if (brMogPoteza == 3 && oppMove == Move.PUT2COINS) { //goody
							this.myMove = Move.DONTPUTCOINS;
							taktika = 0;
						}
						else if (brMogPoteza == 3 && oppMove == Move.PUT1COIN) { //avenger
							this.myMove = Move.PUT1COIN;
							taktika = 1;
							
							if (kradi == true && brMogPoteza == (brPoteza - 1)) {
								this.myMove = Move.DONTPUTCOINS;
							}
						}
						else {
							
							if (kradi == true && brMogPoteza == (brPoteza - 1)) { // poslednji potez
								this.myMove = Move.DONTPUTCOINS;
							}
							else if (taktika == 0) this.myMove = Move.DONTPUTCOINS;
							else if(taktika == 1) this.myMove = Move.PUT1COIN;
						}
					}
					
				}
				
				else if (prviPotez == Move.PUT1COIN) { // copycat /forgiver
					if ( brMogPoteza == 1) {
						this.myMove = Move.DONTPUTCOINS;
					}
					else if ( brMogPoteza == 2) {
						this.myMove = Move.PUT2COINS;
						
						if (kradi == true && brMogPoteza == (brPoteza - 1)) {
							this.myMove = Move.DONTPUTCOINS;
						}
					}
					else { // potez dva i dalje
						if (brMogPoteza == 3 && oppMove == Move.DONTPUTCOINS) { //copycat
							this.myMove = Move.PUT2COINS;
							taktika = 2;
							
							if (kradi == true && brMogPoteza == (brPoteza - 1)) {
								this.myMove = Move.DONTPUTCOINS;
							}
						}
						else if (brMogPoteza == 3 && oppMove == Move.PUT2COINS) { // forgiver
							this.myMove = Move.DONTPUTCOINS;
							taktika = 20;
							
							if (kradi == true) {
								
								if(brMogPoteza == (brPoteza - 1))
								   this.myMove = Move.DONTPUTCOINS;
								else if (brMogPoteza == (brPoteza - 2) && sviMojiPotezi.get(brMogPoteza-1)== Move.PUT2COINS)
									this.myMove = Move.DONTPUTCOINS;
							}
						}
						else {
							if (taktika == 2) {
								this.myMove = Move.PUT2COINS;
								
								if (kradi == true && brMogPoteza == (brPoteza - 1)) {
									this.myMove = Move.DONTPUTCOINS;
								}
								
							}
								
							else if(taktika == 20) { // naizmenicno ubacujem 2 i 0 novcica
								if ((brMogPoteza % 2) == 0) this.myMove = Move.PUT2COINS;
								else this.myMove = Move.DONTPUTCOINS;
								
								if (kradi == true) {
									
									if(brMogPoteza == (brPoteza - 1))
									   this.myMove = Move.DONTPUTCOINS;
									else if (brMogPoteza == (brPoteza - 2) && sviMojiPotezi.get(brMogPoteza-1)== Move.PUT2COINS)
										this.myMove = Move.DONTPUTCOINS;
									
								}
							}
						}
					}
					
				}
				
			}
			
		}
		
		if ( jaBrojim == true) brPoteza++;
		
		sviMojiPotezi.add(myMove);
		brMogPoteza++;
		return myMove;
	}
}
