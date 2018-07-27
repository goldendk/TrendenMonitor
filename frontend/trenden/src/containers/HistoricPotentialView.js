import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {loadRecommendationPeriods} from '../actions/periodsActions'
import Route from 'react-router-dom';
import {Table} from 'react-bootstrap';


class HistoricPotentialView extends Component {

    componentDidMount() {
        console.log("HistoricPotentialView.js - loading recommendation periods");
        this.props.loadRecommendationPeriods();
    }

    render() {
            if (this.props.historicPotential == null || this.props.historicPotential.records.length == 0) {
            console.log("RENDER: " + this.props.historicPotential);
            return (<div>No data - loading</div>)
        }


        return (
            <div>
                <div>Potential earnings: {this.props.historicPotential.potentialEarnings}</div>
                <div>Potential losses: {this.props.historicPotential.potentialLoss}</div>


                <Table striped bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Days</th>
                        <th>Signal</th>
                        <th>Start value</th>
                        <th>Latest value</th>
                        <th>Potential</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.props.historicPotential.records.map(function (e) {
                        console.log("ITERATING");
                        return <tr  key={e.id}>
                            <td>{e.name}</td>
                            <td>{e.startDate}</td>
                            <td>{e.endDate}</td>
                            <td>{e.days}</td>
                            <td>{e.startSignal}</td>
                            <td>{e.startValue}</td>
                            <td>{e.endValue}</td>
                            <td>{e.potential}</td>
                        </tr>
                    })}


                    </tbody>
                </Table>
            </div>


        )
    }
}

function mapStateToProps({historicPotential}, ownProps) {
    console.log("HistoricPotentialView.js, mapStateToProps", historicPotential);
    return {
        ...historicPotential
    }
}

export default connect(mapStateToProps, {loadRecommendationPeriods})(HistoricPotentialView);