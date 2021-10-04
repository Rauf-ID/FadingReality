package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.dialogs.Conversation;
import com.mygdx.game.dialogs.ConversationChoice;
import com.mygdx.game.dialogs.ConversationGraph;
import com.mygdx.game.entity.EntityConfig;


import java.util.ArrayList;

public class ConversationUI extends Window {
    private static final String TAG = ConversationUI.class.getSimpleName();

    private final Label dialogText;
    private final List listItems;
    private ConversationGraph graph;
    private String currentEntityID;

    private final TextButton _closeButton;

    private final Json json;

    public ConversationUI(final Skin skin) {


        super("dialog", skin, "default");


        json = new Json();
        graph = new ConversationGraph();

        //create
        dialogText = new Label("No Conversation", skin);
        dialogText.setWrap(true);
        dialogText.setAlignment(Align.center);
        listItems = new List<ConversationChoice>(skin);

        _closeButton = new TextButton("X", skin);

        final ScrollPane scrollPane = new ScrollPane(listItems, skin, "default");
        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(true, false);
        scrollPane.setScrollBarPositions(false, true);

        //layout
        add();
        getTitleTable().add(_closeButton);
        row();

        defaults().expand().fill();
        add(dialogText).pad(20, 50, 20, 50);
        row();
        add(scrollPane).pad(10,10,10,10);

        add(listItems);
        pack();

        //Listeners
        listItems.addListener(new ClickListener() {
                                   @Override
                                   public void clicked (InputEvent event, float x, float y) {
                                       ConversationChoice choice = (ConversationChoice)listItems.getSelected();
                                       if( choice == null ) return;
                                       graph.notify(graph, choice.getConversationCommandEvent());
                                       populateConversationDialog(choice.getDestinationId());
                                   }
                               }
        );
    }

    public ConversationGraph getConversationGraph(){
        return graph;
    }

    public void loadConversation(EntityConfig entityConfig){
        String fullFilenamePath = entityConfig.getConversationConfigPath();
        this.getTitleLabel().setText("");

        clearDialog();

        if( fullFilenamePath.isEmpty() || !Gdx.files.internal(fullFilenamePath).exists() ){
            Gdx.app.debug(TAG, "Conversation file does not exist!");
            return;
        }

        currentEntityID = entityConfig.getEntityID();
        this.getTitleLabel().setText(entityConfig.getEntityID());

        ConversationGraph graph = json.fromJson(ConversationGraph.class, Gdx.files.internal(fullFilenamePath));
        setConversationGraph(graph);
    }

    public void setConversationGraph(final ConversationGraph graph){
        if(graph != null ) graph.removeAllObservers();
        this.graph = graph;
        this.populateConversationDialog(graph.getCurrentConversationID());
    }

    public void delConversationUI(){
        remove();
    }

    private void populateConversationDialog(final String conversationID){
        this.clearDialog();

        final Conversation conversation = this.graph.getConversationByID(conversationID);
        if( conversation == null ) return;
        graph.setCurrentConversation(conversationID);
        dialogText.setText(conversation.getDialog());
        final ArrayList<ConversationChoice> choices = graph.getCurrentChoices();
        if( choices == null ) return;

        listItems.setItems(choices.toArray());
        listItems.setSelectedIndex(-1);
    }
    public TextButton getCloseButton(){
        return _closeButton;
    }

    public String getCurrentEntityID() {
        return currentEntityID;
    }

    public ConversationGraph getCurrentConversationGraph(){
        return graph;
    }

    private void clearDialog(){
        dialogText.setText("");
        listItems.clearItems();
    }

    @Override
    public void act(float delta) {

    }

}