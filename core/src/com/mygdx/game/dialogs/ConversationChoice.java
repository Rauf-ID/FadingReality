package com.mygdx.game.dialogs;

import com.mygdx.game.observer.ConversationGraphObserver.ConversationCommandEvent;

public class ConversationChoice {
    private String sourceId; //Conversation из которого мы двигаемся
    private String destinationId; //Conversation, куда мы хотим попасть.
    private String choicePhrase;
    private ConversationCommandEvent conversationCommandEvent;

    public ConversationChoice() {}

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId){
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId){
        this.destinationId = destinationId;
    }

    public String getChoicePhrase() {
        return choicePhrase;
    }

    public void setChoicePhrase(String choicePhrase) {
        this.choicePhrase = choicePhrase;
    }

    public ConversationCommandEvent getConversationCommandEvent() {
        return conversationCommandEvent;
        //ConversationCommandEvent - один из объектов перечисления в ConversationGraphObserver.
    }

    public void setConversationCommandEvent(ConversationCommandEvent choiceCommand) {
        this.conversationCommandEvent = choiceCommand;
    }

    public String toString(){
        return choicePhrase;
    }

}