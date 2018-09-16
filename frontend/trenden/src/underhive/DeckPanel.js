import React, {Component} from 'react';
import {Panel, ButtonGroup, Glyphicon, Button} from 'react-bootstrap'
import CardDrawPanel from './CardDrawPanel'
import {printDateTimeFromLong} from '../Util'

class DeckPanel extends Component {

    constructor(props, context) {
        super(props, context);
       this.onArchiveClick =  this.onArchiveClick.bind(this);
    }

    onArchiveClick() {
        if (window.confirm('Really remove deck?')) {
            this.props.onArchiveDeck(this.props.deck.id);
        }
    }

    render() {

        return (<div>
            <Panel>
                <Panel.Heading>
                    <Panel.Title componentClass="h3">{printDateTimeFromLong(this.props.deck.createdTs)}
                        <div className={"flex-div"} style={{float: "right", cursor: "pointer"}}>
                            <Glyphicon onClick={this.onArchiveClick} style={{float: "right"}} glyph={"remove"}/>
                        </div>
                    </Panel.Title>
                </Panel.Heading>
                <Panel.Body>
                    {this.props.deck.draws.map((drawObj, idx) => {
                        return <CardDrawPanel onUndoDraw={this.props.onUndoDraw}
                                              toggleCardUse={this.props.toggleCardUse}
                                              key={idx} idxVal={idx}
                                              deckId={this.props.deck.id}
                                              cardDraw={drawObj}/>
                    })}
                </Panel.Body>
            </Panel>
        </div>);


    }
}

export default DeckPanel;