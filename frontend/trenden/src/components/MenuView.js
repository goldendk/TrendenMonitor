import React, {Component} from 'react';

import Route from 'react-router-dom';
import {Nav,Navbar, NavItem, MenuItem, NavDropdown } from 'react-bootstrap';


class MenuView extends Component {
    render() {
        return (
            <Navbar staticTop>
                <Nav>
                    <NavItem eventKey={0} href="/open-recommendations-view">
                        Open recommendations
                    </NavItem>
                    <NavItem eventKey={1} href="/historic-potential-view">
                        Historic potential
                    </NavItem>
                    <NavItem eventKey={2} href="#">
                        Link
                    </NavItem>
                    <NavDropdown eventKey={3} title="Dropdown" id="basic-nav-dropdown">
                        <MenuItem eventKey={3.1}>Action</MenuItem>
                        <MenuItem eventKey={3.2}>Another action</MenuItem>
                        <MenuItem eventKey={3.3}>Something else here</MenuItem>
                        <MenuItem divider />
                        <MenuItem eventKey={3.4}>Separated link</MenuItem>
                    </NavDropdown>
                </Nav>
            </Navbar>
        )
    }
}
export default MenuView;