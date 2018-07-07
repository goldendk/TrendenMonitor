import React, {Component} from 'react';

import Route from 'react-router-dom';
import {Table, Grid} from 'react-bootstrap';
import {load} from '../actions/index';


class OpenRecommendationsView extends Component {

    constructor(props, context) {
        super();
        this.state = {openList: []};
    }

    componentDidMount() {
        var _this = this;
        window.backend.loadOpenRecommendations(function (data) {
            _this.setState({openList: data});
        });
    }


    render() {
        if (this.state == null || this.state.openList == null) {
            return <div>Loading...</div>
        }

        return (
            <Table striped bordered condensed hover className={'center-text'}>
                <thead>
                <tr className={'center-text2'}>
                    <th>Name</th>
                    <th>Start Date</th>
                    <th>Signal signal</th>
                    <th>Signal value</th>
                    <th>Days</th>
                    <th>Latest value</th>
                </tr>
                </thead>
                <tbody>
                {this.state.openList.map(function (rowData) {
                    return <tr key={rowData.name}>
                        <td>{rowData.name}</td>
                        <td>{rowData.startDate}</td>
                        <td>{rowData.startSignal}</td>
                        <td>{rowData.startValue}</td>
                        <td>{rowData.periodDays}</td>
                        <td>{rowData.latestValue}</td>

                    </tr>
                })}


                </tbody>
            </Table>


        )
    }
}

export default OpenRecommendationsView;