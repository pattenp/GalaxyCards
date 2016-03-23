package cardCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import cards.Deck;
import cards.Hero;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import guiPacket.Card;

/**
 * The class methods for creating and loading decks.
 * @author patriklarsson
 *
 */
public class CreateController {
	private Deck activeDeck = new Deck();
	private CreateGui gui;
	private final String DECKPATH = "files/decks/";
	
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
			ResourceCard card = new ResourceCard();
			activeDeck.addResoruceCard(card);
			gui.addCardToList(card);
		}
	}
	
	public void addUnitCard(String name, String rarity, String imageName, boolean hasAbility, int attack, int defense, int price, 
			String description) {
		Unit cardToAdd = new Unit(name, rarity, imageName, hasAbility, attack, defense, price);
		cardToAdd.setAbilityText(description);
		activeDeck.addUnitCard(cardToAdd);
		gui.addCardToList(cardToAdd);
		
	}
	
	public void addTechCard(String name, String rarity, String imageName, int price) {
		Tech cardToAdd = new Tech(name, rarity, imageName, price);
		activeDeck.addTechCard(cardToAdd);
		gui.addCardToList(cardToAdd);
		
	}
	
	public void addHeroicSupportCard(String name, String rarity, String imageName, boolean hasAbility, int price, int defense) {
		HeroicSupport cardToAdd = new HeroicSupport(name, rarity, imageName, hasAbility, price, defense);
		activeDeck.addHeroicSupportCard(cardToAdd);
		gui.addCardToList(cardToAdd);
	}
	
	
	/**
	 * Writes the activeDeck to a dat file with the name of the deck in the decks folder.
	 */
	public void saveDeckToFile() {
		String filename = DECKPATH + "test.dat"; //+ activeDeck.getName();
		File file = new File(filename);
		try(FileOutputStream fout = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fout)) {
				oos.writeObject(activeDeck);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeCardFromList(Card card) {
		gui.removeCardFromList(card);
		activeDeck.removeCard(card);
	}
	
	/**
	 * Reads a deck from a dat file and sets the activeDeck to the read deck
	 * @param filename The name of the file with the deck
	 */
	public void readDeckFromFile() {
		// TODO Ersätt denna kod
		String fileName = JOptionPane.showInputDialog("Ange namn på deck");
		File file = new File(DECKPATH + fileName + ".dat");
		try(
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin)) {
			this.activeDeck = (Deck)ois.readObject();
		} catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		printDeck();
	}
	
	private void printDeck() {
		for (int i = 0; i < activeDeck.getAmtOfCards(); i++) {
			Card card = activeDeck.getCard(i);
			gui.addCardToList(card);	
		}
	}
	
	
	public void previewCard() {
		gui.updateCardPreview();
	}
	
	public Deck getActiveDeck() {
		return this.activeDeck;
	}
	
	public void setActiveDeck(Deck deck) {
		this.activeDeck = deck;
	}
	

	public void listItemSelected(Card selectedCard) {
		if(selectedCard instanceof Unit){
			Unit card = (Unit)selectedCard;
			gui.updateUnitFields(card);
		}
		else if( selectedCard instanceof Tech){
			Tech card = (Tech)selectedCard;
			gui.updateTechFields(card);
		}
		else if( selectedCard instanceof ResourceCard){
			ResourceCard card = (ResourceCard)selectedCard;
			gui.updateResourceFields(card);
		}
		else if( selectedCard instanceof HeroicSupport){
			HeroicSupport card = (HeroicSupport)selectedCard;
			gui.updateHeroicFields(card);
		}
		
	}
}