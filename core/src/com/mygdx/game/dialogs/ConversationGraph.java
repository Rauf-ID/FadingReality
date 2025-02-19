package com.mygdx.game.dialogs;

import com.badlogic.gdx.utils.Json;
import com.mygdx.game.observer.ConversationGraphSubject;

import java.util.ArrayList;
import java.util.Hashtable;

public class ConversationGraph extends ConversationGraphSubject {

    private Hashtable<String, Conversation> conversations; // Вершины графа
    private Hashtable<String, ArrayList<ConversationChoice>> associatedChoices; // Ребра графа
    private String currentConversationID = null; // Текущий этап диалога

    public ConversationGraph() {}

    public ConversationGraph(Hashtable<String, Conversation> conversations, String rootID){
        setConversations(conversations);
        setCurrentConversation(rootID);
    }

    public void setConversations(Hashtable<String, Conversation> conversations) {
        if(conversations.size() < 0){
            throw new IllegalArgumentException("Can't have a negative amount of conversations");
        }

        this.conversations = conversations;

        this.associatedChoices = new Hashtable<String, ArrayList<ConversationChoice>>(conversations.size());

        for( Conversation conversation: conversations.values() ){
            associatedChoices.put(conversation.getId(), new ArrayList<ConversationChoice>());
        }
    }

    public ArrayList<ConversationChoice> getCurrentChoices(){
        return associatedChoices.get(currentConversationID);
    }

    public boolean isValid(String conversationID){
        Conversation conversation = conversations.get(conversationID);
        if( conversation == null ) return false;
        return true;
    }

    public boolean isReachable(String sourceID, String sinkID){
        if( !isValid(sourceID) || !isValid(sinkID) ) return false;

        if( conversations.get(sourceID) == null ) return false;

        ArrayList<ConversationChoice> list = associatedChoices.get(sourceID);

        if( list == null ) return false;

        for(ConversationChoice choice: list){
            if(choice.getSourceId().equalsIgnoreCase(sourceID) && choice.getDestinationId().equalsIgnoreCase(sinkID) ){
                return true;
            }
        }
        return false;
    }

    public void setCurrentConversation(String id){
        Conversation conversation = getConversationByID(id);
        if( conversation == null ) return;

        if(currentConversationID == null || currentConversationID.equalsIgnoreCase(id) || isReachable(currentConversationID, id) ){
            currentConversationID = id;
        }else{
            //System.out.println("New conversation node [" + id +"] is not reachable from current node [" + currentConversationID + "]");
        }
    }

    public Conversation getConversationByID(String id){
        if( !isValid(id) ){
            return null;
        }
        return conversations.get(id);
    }

    public String displayCurrentConversation(){
        return conversations.get(currentConversationID).getDialog();
    }

    public void addChoice(ConversationChoice conversationChoice) {
        ArrayList<ConversationChoice> list = associatedChoices.get(conversationChoice.getSourceId());
        if (list == null){
            return;
        }

        list.add(conversationChoice);
    }

    public Conversation getNextChoice(String input){

        ArrayList<ConversationChoice> choices = this.getCurrentChoices();
        for(ConversationChoice choice: choices){
            //System.out.println(choice.getDestinationId() + " " + choice.getChoicePhrase());
        }
        Conversation choice = null;
        try {
            choice = this.getConversationByID(input);
            System.out.println(input);
        }catch( NumberFormatException nfe){
            return null;
        }
        return choice;
    }

    public String toJson(){
        Json json = new Json();
        return json.prettyPrint(this);
    }

    public String getCurrentConversationID(){
        return this.currentConversationID;
    }

}
