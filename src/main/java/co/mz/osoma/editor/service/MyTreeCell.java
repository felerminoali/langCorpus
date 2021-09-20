/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mz.osoma.editor.service;

import co.mz.osoma.editor.modelo.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;

import co.mz.osoma.editor.controlador.MainGUIController;

/**
 * @author Lenovo
 */
public class MyTreeCell extends TextFieldTreeCell<Object> {

    private ContextMenu corpusMenu, lineMenu;

    private MainGUIController controller;

    public MyTreeCell(MainGUIController mainGUIController) {

        this.controller = mainGUIController;



        corpusMenu  = ContextMenuBuilder.create()
                .items(
                        MenuItemBuilder.create()
                                .text("Add Line")
                                .onAction(
                                        new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent arg0) {

                                                try {
                                                    Line line = new Line();
                                                    ((SubCorpus)mainGUIController.getSeletedItem().getValue()).getLines().add(line);
                                                    TreeItem<Object> node = mainGUIController.makeBranch(line, mainGUIController.getSeletedItem());
                                                    int row = mainGUIController.treeCon.getRow(node);
                                                    mainGUIController.treeCon.getSelectionModel().select(row);
                                                    mainGUIController.treeCon.scrollTo(row);

                                                }catch (Exception e){

                                                }
                                            }
                                        }
                                )
                                .build()
                )
                .build();

        lineMenu = ContextMenuBuilder.create()
                .items(
                        MenuItemBuilder.create()
                                .text("Delete")
                                .onAction(
                                        new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent arg0) {
                                                TreeItem<Object> objectTreeItem = mainGUIController.getSeletedItem();
                                                ((SubCorpus)objectTreeItem.getParent().getValue()).getLines().remove((Line) objectTreeItem.getValue());
                                                boolean remove = objectTreeItem.getParent().getChildren().remove(objectTreeItem);
                                            }
                                        }
                                )
                                .build(),
                        // other menu item
                        MenuItemBuilder.create()
                                .text("Add Line Below")
                                .onAction(
                                        new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent arg0) {

                                                try {
                                                    Line line = new Line();
                                                    ((SubCorpus)mainGUIController.getSeletedItem().getParent().getValue()).getLines().add(line);
                                                    TreeItem<Object> node = mainGUIController.makeBranch(line, mainGUIController.getSeletedItem().getParent());
                                                    int row = mainGUIController.treeCon.getRow(node);
                                                    mainGUIController.treeCon.getSelectionModel().select(row);
                                                    mainGUIController.treeCon.scrollTo(row);

                                                }catch (Exception e){

                                                }
                                            }
                                        }
                                )
                                .build()

                )
                .build();

    }

    @Override
    public void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);


//        if (!empty && getTreeItem().isLeaf()) {
//            setContextMenu(null);
//            return;
//        }

        if(!empty && item instanceof RootObject){
            setContextMenu(null);
            return;
        }

//        if(!empty && item instanceof Exam){
//            setContextMenu(examMenu);
//            return;
//        }

        if(!empty && item instanceof SubCorpus){
            setContextMenu(corpusMenu);
            return;
        }
//
//        if(!empty && item instanceof Question){
//            setContextMenu(questionMenu);
//            return;
//        }

        if(!empty && item instanceof Line){
            setContextMenu(lineMenu);
            return;
        }

//        if(!empty && item instanceof Choice){
//            setContextMenu(choiceMenu);
//            return;
//        }
//        setContextMenu(questionMenu);
    }
}
