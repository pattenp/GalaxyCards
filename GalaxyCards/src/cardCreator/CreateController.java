package cardCreator;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

import javax.swing.DefaultListModel;

import cards.Card;
import cards.Deck;
import cards.Hero;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;

/**
 * Contatins the logic needed for creating a new <code>Hero</code>. A Hero contatins a <code>Deck</code>
 * of <code>Card</code>
 * @author patriklarsson
 *
 */
public class CreateController {
	private Deck activeDeck = new Deck();
	private CreateGui gui;
	
	public void setGui(CreateGui gui) {
		this.gui = gui;
	}
	
	public Hero createHero() {
		return null;
	}
	
	public Deck createDeck() {
		return new Deck();
	}
	
	public void addResoruceCard(int nbrOfResoruceCards) {
		for (int i = 0; i < nbrOfResoruceCards; i++) {
			activeDeck.addResoruceCard(new ResourceCard());
			
		}
	}
	
	public void addUnitCard(String name, int attack, int defense, int price ) {
		Unit cardToAdd = new Unit(name, attack, defense, price);
		activeDeck.addUnitCard(cardToAdd);
		gui.addCardToList(cardToAdd);
		
	}
	
	public void addTechCard() {
		Tech cardToAdd = new Tech();
		activeDeck.addTechCard(cardToAdd);
	}
	
	public void addHeroicSupportCard(String name, String rarity, String imageName, boolean hasAbility, int price, int defense) {
		HeroicSupport cardToAdd = new HeroicSupport(name, rarity, imageName, hasAbility, price, defense);
		activeDeck.addHeroicSupportCard(cardToAdd);
	}
	
	public HeroicSupport createHeroicSupportCard() {
		return null;
	}
	
	public void saveHeroToFile() {
	}
	
	/**
	 * Writes the activeDeck to a dat file with the name of the deck in the decks folder.
	 */
	public void saveDeckToFile() {
		String filename = "files/decks/" + "test"; //+ activeDeck.getName();
		File file = new File(filename);
		try(FileOutputStream fout = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fout)) {
				oos.writeObject(activeDeck);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads a deck from a dat file and sets the activeDeck to the read deck
	 * @param filename The name of the file with the deck
	 */
	public void readDeckFromFile(String filename) {
		File file = new File(filename);
		try{
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.activeDeck = (Deck)ois.readObject();
		} catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void previewCard() {
		
	}
	
	public Deck getActiveDeck() {
		return this.activeDeck;
	}
	
	public void setActiveDeck(Deck deck) {
		this.activeDeck = deck;
	}
	
	public static void main(String[] args) {
	}
}
