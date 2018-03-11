import React, {Component} from 'react';

import Route from 'react-router-dom';
import {Table } from 'react-bootstrap';


class HistoricPotentialView extends Component {
    render() {


        return (
            <Table striped bordered condensed hover>
        <thead>
        <tr>
        <th>Name</th>
        <th>Start Date</th>
        <th>Signal signal</th>
        <th>Signal value</th>
        <th>Days</th>
        <th>Latest value</th>
    </tr>
    </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
        </tr>
        <tr>
            <td>3</td>
            <td colSpan="2">Larry the Bird</td>
            <td>@twitter</td>
        </tr>
        </tbody>
    </Table>


        )
    }
}

export default HistoricPotentialView;