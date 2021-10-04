package com.mygdx.game.dialogs;

import com.badlogic.gdx.utils.Json;
import com.mygdx.game.observer.ConversationGraphSubject;

import java.util.ArrayList;
import java.util.Hashtable;

public class ConversationGraph extends ConversationGraphSubject {

    private Hashtable<String, Conversation> conversations; //Вершины графа
    private Hashtable<String, ArrayList<ConversationChoice>> associatedChoices; //Ребра графа
    private String currentConversationID = null; //Текущий этап диалога

    public ConversationGraph(){

    }

    public ConversationGraph(Hashtable<String, Conversation> conversations, String rootID){
        setConversations(conversations);
        setCurrentConversation(rootID);
    }

    public void setConversations(Hashtable<String, Conversation> conversations) {
        if( conversations.size() < 0 ){
            throw new IllegalArgumentException("Can't have a negative amount of conversations");
        }
        //Проверка, делающая невозможным задание conversation'ов меньше нуля.

        this.conversations = conversations;
        //Если всё нормально, то передаем наши conversation'ы ConversationGraph'у.

        this.associatedChoices = new Hashtable<String, ArrayList<ConversationChoice>>(conversations.size());
        //associatedChoices - ребра графа.Создаем словарь, равный по длине числу conversations/
        //соответственно, для каждого conversation будет от нуля до нескольких ребёр - выборов.

        for( Conversation conversation: conversations.values() ){
            associatedChoices.put(conversation.getId(), new ArrayList<ConversationChoice>());
        }
        //Обходим все наши conversation и достаем из них значения. В словарь ребер заносим значения
        //id и пустой массив
    }

    public ArrayList<ConversationChoice> getCurrentChoices(){
        return associatedChoices.get(currentConversationID);
    }

    public String getCurrentConversationID(){
        return this.currentConversationID;
    }



    public boolean isValid(String conversationID){
        Conversation conversation = conversations.get(conversationID);
        if( conversation == null ) return false;
        return true;
        //Метод для проверки существования conversation. Если существует, возвращает true, иначе - false.
    }

    public boolean isReachable(String sourceID, String sinkID){
        //Метод проверяет, доступен ли conversation sinkID для conversation sourceID, то есть можно
        //ли какими-либо выборами достичь точки назначения из текущего этапа диалога. Метод возвращает
        //true если можно достичь, иначе - false.

        if( !isValid(sourceID) || !isValid(sinkID) ) return false;
        //Если одного из conversation'ов не существует, возвращаем сразу false.

        if( conversations.get(sourceID) == null ) return false;
        //Если conversation существует, но не находится в данном  диалоге, тоже false.

        ArrayList<ConversationChoice> list = associatedChoices.get(sourceID);
        //Берем все выборы, которые есть в диалоге.
        if( list == null ) return false;
        //Если выборов нет, то false.

        for(ConversationChoice choice: list){
            if(     choice.getSourceId().equalsIgnoreCase(sourceID) &&
                    choice.getDestinationId().equalsIgnoreCase(sinkID) ){
                return true;
            }
        }
        //Обходим массив выборов, если id выбора начала и id conversation, от которого мы начали двигаться,
        //равны, и равны id выбора конца и id conversation конца, то возвращаем true.

        return false;
    }

    public void setCurrentConversation(String id){
        Conversation conversation = getConversationByID(id);
        if( conversation == null ) return;

        //Идёт проверка на существования данного conversation по id. Если не существует- то останавливаем метод.


        if(     currentConversationID == null ||
                currentConversationID.equalsIgnoreCase(id) ||
                isReachable(currentConversationID, id) ){
            currentConversationID = id;

            //Если у нас еще нет текущего conversation или мы передали в этот метод id УЖЕ текущего conversation
            // или же мы можем достичь id из текущего conversation, то ставим текущим conversation id.
        }else{
            //System.out.println("New conversation node [" + id +"] is not reachable from current node [" + currentConversationID + "]");

            //Иначе,мы не можем до него дойти. Ничего не делаем.
        }
    }

    public Conversation getConversationByID(String id){
        if( !isValid(id) ){
            return null;
        }
        return conversations.get(id);

        // Проверяем, существует ли conversation с данным id, да - возвращаем этот conversation
        //иначе - null.
    }

    public String displayCurrentConversation(){
        return conversations.get(currentConversationID).getDialog();

        //Возвращает текст диалога из текущего Conversation.
    }

    public void addChoice(ConversationChoice conversationChoice) {

        ArrayList<ConversationChoice> list = associatedChoices.get(conversationChoice.getSourceId());
        if (list == null){
            return;
        }

        list.add(conversationChoice);
        //Метод добавляет ребро choice между двумя вершинами, содержащимеся в объекте conversationChoice,
        //к текущему списку выборов.
    }



    public String toJson(){
        Json json = new Json();
        return json.prettyPrint(this);
    }
    public Conversation getNextChoice(String _input){

        ArrayList<ConversationChoice> choices = this.getCurrentChoices();
        for(ConversationChoice choice: choices){
            //System.out.println(choice.getDestinationId() + " " + choice.getChoicePhrase());
        }
        Conversation choice = null;
        try {
            choice = this.getConversationByID(_input);
            System.out.println(_input);
        }catch( NumberFormatException nfe){
            return null;
        }
        return choice;
    }
}
