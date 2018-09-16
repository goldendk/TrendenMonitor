import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {loadCardDeckList} from '../actions/underhiveActions'
import Route from 'react-router-dom';
import {Table, Panel} from 'react-bootstrap';
import DeckPanel from './DeckPanel'
import PotentialFilterForm from '../components/PotentialFilterForm'
import CardDrawPanel from "./CardDrawPanel";

class DeckListContainer extends Component {
    constructor(props, context) {
        super(props, context);
    }


    componentDidMount(){

        this.props.loadCardDeckList();

    }

    render(){

        if(this.props.decks == null){
            return (<div>Loading your decklists...</div>)
        }
        return (<div>

            {this.props.decks.map(deck=>{return <DeckPanel onArchiveDeck={this.props.onArchiveDeck}
                                                           toggleCardUse={this.props.toggleCardUse}
                                                           onUndoDraw={this.props.onUndoDraw} key={deck.id} deck={deck} />}) }


        </div>)

    }

}

function mapStateToProps({underhiveState}, ownProps) {
    console.log("DeckListContainer.js, mapStateToProps", underhiveState);
    return {
        ...underhiveState
    }
}

export default connect(mapStateToProps, {loadCardDeckList})(DeckListContainer);


