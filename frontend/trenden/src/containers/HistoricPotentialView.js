import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {loadRecommendationPeriods} from '../actions/periodsActions'
import Route from 'react-router-dom';
import {Table } from 'react-bootstrap';


class HistoricPotentialView extends Component {

    componentDidMount(){
        console.log("HistoricPotentialView.js - loading recommendation periods");
        this.props.loadRecommendationPeriods();
    }

    render() {
        if(this.props.recommendationPeriods == null|| this.props.recommendationPeriods.length == 0){
            console.log("RENDER: " + this.props.recommendationPeriods);
            return (<div>No data - loading</div>)
        }


        return (
            <Table striped bordered condensed hover>
        <thead>
        <tr>
        <th>Name</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Signal signal</th>
        <th>Signal value</th>
        <th>Days</th>
        <th>Latest value</th>
        <th>Potential</th>
    </tr>
    </thead>
        <tbody>

        {this.props.recommendationPeriods.map(function (e){
            console.log("ITERATING");
             return <tr key={e.id}>
                    <td>{e.name}</td>
                    <td>{e.startDate}</td>
                    <td>{e.endDate}</td>
                    <td>{e.startSignal}</td>
                    <td>{e.startValue}</td>
                    <td>{e.periodDays}</td>
                    <td>{e.latestValue}</td>
                    <td>{e.endSignal == null || e.startSignal == 'SELL' ? 0 : Math.round(100000 / e.startValue) * (e.endValue - e.startValue)}</td>
                </tr>
        })}


        </tbody>
    </Table>


        )
    }
}
function mapStateToProps({recommendationPeriods}, ownProps) {
    console.log("HistoricPotentialView.js, mapStateToProps", recommendationPeriods);
    return {
        ...recommendationPeriods
    }
}

export default connect(mapStateToProps, {loadRecommendationPeriods})(HistoricPotentialView);