import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router-dom';
import {bindActionCreators} from 'redux';
import {loadCardDeckList, loadUnderHiveMeta, drawRandomCards, archiveDeck, undoDraw} from '../actions/underhiveActions'
import {
    Table,
    Panel,
    Checkbox,
    FormGroup,
    ControlLabel,
    ButtonToolbar,
    ToggleButtonGroup,
    ToggleButton,
    Button
} from 'react-bootstrap';
import DeckListContainer from './DeckListContainer'

class CardDrawView extends Component {

    constructor(props, context) {
        super(props, context);
    }

    componentDidMount() {
        this.props.loadUnderHiveMeta();
    }


    render() {

        if (this.props.meta == null) {
            return (<div>Loading meta-data...</div>)
        }


        return (<div>
            <div>

                <form id={this.props.formId} name={this.props.formId} method="POST"
                      onSubmit={event => {
                          event.preventDefault();
                          this.props.drawRandomCards(event)
                      }}>
                    <FormGroup md={6}>
                        <ControlLabel>Gangs</ControlLabel>
                        {this.props.meta.gangs.map(
                            gang => <Checkbox key={gang.gangSymbol} name="affilationFilter"
                                              value={gang.gangSymbol}>{gang.label}</Checkbox>
                        )}
                    </FormGroup>
                    <FormGroup md={6}>
                        <ControlLabel>New deck</ControlLabel>

                    </FormGroup>

                    <FormGroup md={6}>
                        <ControlLabel>Card count</ControlLabel>
                        <ButtonToolbar>
                            <ToggleButtonGroup type="radio" name="cardCount" defaultValue={1}>
                                <ToggleButton key={"1"} value={1}>1 card</ToggleButton>
                                <ToggleButton key={"2"} value={2}>2 cards</ToggleButton>
                                <ToggleButton key={"3"} value={3}>3 cards</ToggleButton>
                                <ToggleButton key={"4"} value={4}>4 cards</ToggleButton>
                            </ToggleButtonGroup>
                        </ButtonToolbar>

                    </FormGroup>
                    <Button bsStyle="primary" type="submit">Draw Random</Button>
                    <div/>

                    <Link to="/underhive/SelectTacticCardsView">
                        <Button bsStyle="success">Select cards manually</Button>
                    </Link>
                </form>


            </div>

            {(this.props.decks != null && this.props.decks.length === 0) ? (<div>Draw some cards...</div>) :
                <DeckListContainer onArchiveDeck={this.props.archiveDeck} onUndoDraw={this.props.undoDraw}/>}

        </div>)
    }

}

function mapStateToProps({underhiveState}, ownProps) {
    console.log("underhive/CardDrawView.js, mapStateToProps", underhiveState);
    return {
        ...underhiveState
    }
}

export default connect(mapStateToProps, {
    loadCardDeckList,
    loadUnderHiveMeta,
    drawRandomCards,
    undoDraw,
    archiveDeck
})(CardDrawView);


