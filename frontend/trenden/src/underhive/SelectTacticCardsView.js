import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {loadTacticCardList, loadUnderHiveMeta,toggleTacticCard, drawSelectedTacticCards} from '../actions/underhiveActions'
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

class SelectTacticCardsView extends Component {

    constructor(props, context) {
        super(props, context);
    }

    componentDidMount() {
        this.props.loadTacticCardList(true);
        this.props.loadUnderHiveMeta();
        this.toggleTacticCard = this.toggleTacticCard.bind(this);
        this.renderCardRows = this.renderCardRows.bind(this);
        this.drawSelectedCards = this.drawSelectedCards.bind(this);
    }

    toggleTacticCard(cardId){
        this.props.toggleTacticCard(cardId)
    }

    drawSelectedCards(event){
        event.preventDefault();
        if(Object.keys(this.props.selectedTacticCards).length == 0){
            alert("no cards selected");
            return;
        }
        this.props.drawSelectedTacticCards(this.props.selectedTacticCards, ()=>{this.props.history.push("/underhive/CardDrawView")}, ()=>{alert('not ok')});
    }

    renderCardRows() {
        var deckSets = {"common": []};

        this.props.tacticCardList.map(function (card) {
            var gangAff = card.gangAffiliation;
            if (gangAff == null) {
                gangAff = "common";
            }
            if (deckSets[gangAff] == null) {
                deckSets[gangAff] = [];
            }
            deckSets[gangAff].push(card);

        });

        for (var k in deckSets) {
            deckSets[k].sort((a, b) => a.name.localeCompare(b.name));
        }

        var setOrder = Object.keys(deckSets);
        setOrder.sort((a, b) => {
            if (a == 'common') {
                return 1;
            }
            if (b == 'common') {
                return -1;
            }
            return a.localeCompare(b);
        });
        var that = this;

        return setOrder.map((setKey => {
            var setCards = deckSets[setKey];
            var header = (<tr key={setKey}>
                <td colSpan={3}><h2>{setKey == 'common' ? 'Common set' : that.props.meta.gangMap[setKey].label}</h2></td>
            </tr>)
            var cardRows = setCards.map(card => {
                return (  <tr key={card.id} className={this.props.selectedTacticCards[card.id] != null ? 'info' : ''} onClick={(event)=>this.toggleTacticCard(card.id)}>
                    <td>{card.name}</td>
                    <td>{card.activation}</td>
                    <td>{card.description}</td>
                </tr>)
            });
            return [header, ...cardRows];
        }));

    }

    render() {

        if (this.props.tacticCardList == null) {
            return (<div>Loading cards...</div>)
        }


        return (<div>
            <form onSubmit={this.drawSelectedCards}>
                <Button type={"submit"} bsStyle={"primary"} >Draw selected cards</Button>
            </form>

            <Table striped bordered condensed hover className={'tactic-card-table'}>
            <thead>
            <tr className={'center-text2'}>
                <th>Name</th>
                <th>Activation</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            {this.renderCardRows()}
            </tbody>
        </Table></div>);


    }

}

function mapStateToProps({underhiveState}, ownProps) {
    console.log("underhive/SelectTacticCardsView.js, SelectTacticCardsView", underhiveState);
    return {
        ...underhiveState
    }
}

export default connect(mapStateToProps, {loadTacticCardList, loadUnderHiveMeta, toggleTacticCard,drawSelectedTacticCards})(SelectTacticCardsView);


