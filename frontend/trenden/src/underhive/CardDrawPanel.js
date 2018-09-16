import React, {Component} from 'react';
import {Panel, Glyphicon} from 'react-bootstrap'
import TacticCardPanel from "./TactiCardPanel";

class CardDrawPanel extends Component {

    constructor(props, context) {
        super(props, context);
        this.onUndoDrawClick = this.onUndoDrawClick.bind(this);
    }


    onUndoDrawClick(){
        if(window.confirm('Really undo this draw ?')){
            this.props.onUndoDraw(this.props.deckId, this.props.idxVal);
        }
    }


    render() {

        return (<div>
            <Panel>
                <Panel.Heading>
                    Cards Drawn: {this.props.cardDraw.cards.length}
                    <div className={"flex-div"} style={{float: "right", cursor: "pointer"}}>
                        <Glyphicon alt={"Click to undo draw."} onClick={this.onUndoDrawClick} style={{float: "right"}} glyph={"remove-sign"}/>
                    </div>
                </Panel.Heading>
                <Panel.Body>
                    {this.props.cardDraw.cards.map((cardObj, idx) => {
                        return <TacticCardPanel key={idx} card={cardObj}/>
                    })}
                </Panel.Body>
            </Panel>

        </div>);


    }
}

export default CardDrawPanel;