package Client;

import cards.Unit;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import enumMessage.Phase;
import game.GameController;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;
import move.Attack;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayTechCard;
import move.PlayUnitCard;
import move.TapUntapCard;
import move.UpdateHeroValues;

/**
 * Contains the logic for a Client.
 * 
 * @author Jonte
 *
 */
public class ClientController {
	private Client client;
	private String activeUser;
	private GameController gameController = new GameController(this);
	private ClientGUI clientGUI;
	private boolean loginOK = false;

	public ClientController(ClientGUI clientGUi) {
		this.clientGUI = clientGUi;
	}

	/**
	 * Invokes the disconnect method in the Client class.
	 */
	public void disconnect() {
		client.disconnect();
	}

	/**
	 * Creates a client with a specified ip, port and ClientController.
	 * 
	 * @param ip
	 *            The IP to a Server.
	 * @param port
	 *            The port to a Server.
	 */
	public void connect(String ip, int port) {
		client = new Client(ip, port, this);

	}

	/**
	 * Invokes the initGame method in GameController.
	 */
	public void initGame() {
		gameController.initGame();
	}

	/**
	 * Sends the username a Client has typed in to the server and asks to Login.
	 */
	public synchronized void login() {
		if(clientGUI.getUsername().length()>16){
			clientGUI.appendTextArea("\n Your username can't be more than 16 characters long, try again!" );
		}else if(clientGUI.getUsername().contains(" ")){
			clientGUI.appendTextArea("\n You can't have spacebar in your username, try again!");
			
		}else{
		activeUser = clientGUI.getUsername();
		CommandMessage loginMsg = new CommandMessage(Commands.LOGIN, activeUser);
		client.sendMessage(loginMsg);
		clientGUI.enableSearchBtn();
	
		}
	}

	/**
	 * If the username is valid/available it gives feedback to ClientGUI.
	 * 
	 * @param response
	 *            Response from server if valid username.
	 */
	public void loginAnswer(CommandMessage response) {
		if (response.getCommand() == Commands.LOGIN_OK) {
			loginOK = true;
			clientGUI.appendTextArea("\n Welcome " + activeUser + "!");
			clientGUI.disableEnterBtn();
		} else {
			clientGUI.appendTextArea("\n Username is already in use, enter a new one");
		}
	}
	
	/**
	 * Invokes the appendText() in clientGUI.
	 */
	public void guiMessage(String text){
		clientGUI.appendTextArea(text);
	}
	
	public void enableUsernameBtn(){
		clientGUI.enableUsernameBtn();
	}

	/**
	 * Invokes the sendMessage method in Client.
	 * 
	 * @param message
	 *            The message to be sent.
	 */
	public void writeMessage(CommandMessage message) {
		client.sendMessage(message);
	}

	/**
	 * Sends a message to the server that the client is looking for a game.
	 */
	public void startMatchMaking() {
		// Logging Message used for debug purpose
		System.out.println("Client Controller: " + activeUser + " StartMatchmaking()");
		CommandMessage message = new CommandMessage(Commands.MATCHMAKING_START, activeUser);
		client.sendMessage(message);
	}

	/**
	 * The method is called when the client receives a message with the command
	 * MATCH_PLAYCARD and delegates
	 * 
	 * @param message
	 *            The message to unpack
	 */
	public void cardPlayed(CommandMessage message) {
		Object data = message.getData();
		if (data instanceof PlayUnitCard) {
			PlayUnitCard move = (PlayUnitCard) message.getData();
			gameController.opponentPlaysUnit((Unit) move.getCard(), move.getLane());
		} else if (data instanceof PlayHeroicSupportCard) {
			PlayHeroicSupportCard move = (PlayHeroicSupportCard) data;
			gameController.opponentPlaysHeroic(move.getCard());
		} else if (data instanceof PlayResourceCard) {
			PlayResourceCard move = (PlayResourceCard) data;
			gameController.opponentPlaysResourceCard(move);
			InfoPanelGUI.append("opponent played resource");
		} else if (data instanceof PlayTechCard) {
			PlayTechCard move = (PlayTechCard) data;
			gameController.opponeentPlaysTech(move.getCard());
		}
	}

	/**
	 * Recieves a message object and updates the gui with the corresponding move
	 * 
	 * @param message
	 *            A CommandMessage contating a a move to update the gui with
	 */
	public void placeCard(CommandMessage message) {
		Object obj = message.getData();
		if (obj instanceof PlayHeroicSupportCard) {
			PlayHeroicSupportCard move = (PlayHeroicSupportCard) obj;
			gameController.playHeroicSupportOk(move.getCard());
		} else if (obj instanceof PlayResourceCard) {
			PlayResourceCard move = (PlayResourceCard) obj;
			UpdateHeroValues value = move.getUpdateHeroValues();
			gameController.updatePlayerHeroGui(value.getLife(), value.getEnergyShield(), value.getCurrentResource(),
					value.getMaxResource());
			gameController.playResourceCardOk(move.getCard());
		} else if (obj instanceof PlayUnitCard) {
			PlayUnitCard move = (PlayUnitCard) obj;
			gameController.playUnitOK(move.getCard(), move.getLane());
		} else if (obj instanceof PlayTechCard) {
			PlayTechCard move = (PlayTechCard) obj;
			gameController.playTechOk(move.getCard());
		}
	}

	/**
	 * Calls the GameController to setup a new game when a match is found
	 */
	public void matchFound(CommandMessage message) {
		gameController.startNewGame();
	}

	/**
	 * Invokes the updateOpponentHero() in gameController. 
	 * @param message
	 * 				Message containing data to updateOpponentHero.
	 */
	public void opponentValuesChanged(CommandMessage message) {
		gameController.updateOpponentHero(message);
		System.out.println("Clientcontroller hero values...");
	}
	
	/**
	 * Invokes method opponentDrawCard() in gameController.
	 */
	public void opponentDrawCard() {
		gameController.opponentDrawCard();
	}
	
	/**
	 * Invokes method setPhase() in gameController.
	 * @param phase
	 * 			The phase to set.
	 */
	public void setPhase(Phase phase) {
		gameController.setPhase(phase);
	}
	
	/**
	 * Invokes method notValidMove(Exception) in gameController.
	 * @param message
	 * 				Message containing the Exception to be cast.
	 */
	public void notValidMove(CommandMessage message) {
		gameController.notValidMove((Exception) message.getData());
	}
	
	/**
	 * Invokes method drawCardOk(card) in gameController.
	 * @param message
	 * 				Message containing a card.
	 * 
	 */
	public void friendlyDrawCard(CommandMessage message) {
		gameController.drawCardOk((Card) message.getData());
	}
	
	/**
	 * Invokes the method discardCard(Card) in gameController.
	 * @param message
	 * 				Message containing a card to be removed.
	 */
	public void discardCard(CommandMessage message) {
		gameController.discardCard((Card) message.getData());

	}
	
	/**
	 * Invokes method updateOpponentScrapyard(card)in gameController.
	 * @param message
	 */
	public void addToOpponentScrapYard(CommandMessage message) {
		gameController.updateOpponentScrapYard((Card) message.getData());

	}
	
	/**
	 * Invokes method updatePlayerHeroGui with data from CommandMessage.
	 * @param message
	 * 				Conatins data to update the values.
	 */
	public void playerHeroValuesChanged(CommandMessage message) {
		UpdateHeroValues values = (UpdateHeroValues) message.getData();
		gameController.updatePlayerHeroGui(values.getLife(), values.getEnergyShield(), values.getCurrentResource(),
				values.getMaxResource());

	}
	/**
	 * Invokes method tapCard(id,Enum) in gameController.
	 * @param message
	 * 				Contains the id and Enum for the card.
	 */
	public void tapCard(CommandMessage message) {
		TapUntapCard temp = (TapUntapCard) message.getData();
		gameController.tapCard(temp.getId(), temp.getENUM());
	}

	/**
	 * Is invoked when server sets the client into defensive phase. Unpacks the
	 * message and sends it to the gameController
	 * 
	 * @param message
	 *            The message to unpack
	 */
	public void setDefendingPhase(CommandMessage message) {
		Attack attack = (Attack) message.getData();
		gameController.doDefendMove(attack);
	}
	
	/**
	 * Invokes method untapCard(id,ENUM) in gameController.
	 * @param message
	 * 				Contains the ID and ENUM for the card to be untapped.
	 */
	public void untapCard(CommandMessage message) {
		TapUntapCard temp = (TapUntapCard) message.getData();
		gameController.untapCard(temp.getId(), temp.getENUM());
	}

	/**
	 * Taps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM
	 *            : Lanes
	 */
	public void tapAllInLane(CommandMessage message) {
		Lanes temp = (Lanes) message.getData();
		gameController.tapAllInLane(temp);
	}

	/**
	 * Untaps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM
	 *            : Lanes
	 */
	public void untapAllInLane(CommandMessage message) {
		Lanes temp = (Lanes) message.getData();
		gameController.untapAllInLane(temp);
	}

	/**
	 * Is invoked when the client recives a message to add a card to the
	 * scarpyards
	 * 
	 * @param message
	 *            A CommandMessage that contations the card to add to the scrap
	 *            yard
	 */
	public void addToScrapYard(CommandMessage message) {
		Card card = (Card) message.getData();
		gameController.updateScarpyard(card);

	}

	/**
	 * Sets the gameController into attack phase
	 */
	public void setAttackPhase() {
		gameController.doAttackMove();

	}

	/**
	 * Invokes gameController.update card to update a card with the values from
	 * a card object stored in the CommandMessage
	 * 
	 * @param message
	 *            A CommandMessage with the card to update
	 */
	public void updateCard(CommandMessage message) {
		Card cardUpdate = (Card) message.getData();
		gameController.updateCard(cardUpdate);
	}

	/**
	 * Invokes a method in the gameController to set the friendly hero id to the
	 * id in the CommandMessage
	 * 
	 * @param message
	 * 		A CommanadMessage contating a integer that represents the hero id
	 */
	public void setFriendlyHeroId(CommandMessage message) {
		int id = (Integer) message.getData();
		gameController.setFriendlyHeroId(id);
	}
	
	/**
	 * Invokes a method in the gameController to set the enemy hero id to the
	 * id in the CommandMessage
	 * 
	 * @param message
	 * 		A CommanadMessage contating a integer that represents the hero id
	 */
	public void setEnemyHeroId(CommandMessage message) {
		int id = (Integer) message.getData();
		gameController.setEnemyHeroId(id);
	}
}
