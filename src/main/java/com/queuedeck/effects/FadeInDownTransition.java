/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.effects;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.util.Duration;

/**
 * Animate a fade in down effect on a node
 * 
 * Port of FadeInDown from Animate.css http://daneden.me/animate by Dan Eden
 * 
 * {@literal @}keyframes fadeInDown {
 * 	0% {
 * 		opacity: 0;
 * 		transform: translateY(-20px);
 * 	}
 * 	100% {
 * 		opacity: 1;
 * 		transform: translateY(0);
 * 	}
 * }
 * 
 * @author Jasper Potts
 */
public class FadeInDownTransition extends CachedTimelineTransition {
    /**
     * Create new FadeInDownTransition
     * 
     * @param node The node to affect
     */
    public FadeInDownTransition(final Node node) {
        super(
            node,
            new Timeline(
                    new KeyFrame(Duration.millis(0),    
                        new KeyValue(node.opacityProperty(), 0, WEB_EASE),
                        new KeyValue(node.translateYProperty(), -20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1000),    
                        new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(node.translateYProperty(), 0, WEB_EASE)
                    )
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
    
       public FadeInDownTransition(final MenuBar menuBar) {
        super(
            menuBar,
            new Timeline(
                    new KeyFrame(Duration.millis(0),    
                        new KeyValue(menuBar.opacityProperty(), 0, WEB_EASE),
                        new KeyValue(menuBar.translateYProperty(), -20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1000),    
                        new KeyValue(menuBar.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(menuBar.translateYProperty(), 0, WEB_EASE)
                    )
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
}