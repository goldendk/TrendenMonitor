import React, {Component} from 'react';
import {FormGroup, ControlLabel, Radio, Button, Checkbox, FormControl, Panel} from 'react-bootstrap'

class TactiCardPanel extends Component {

    constructor(props, context) {
        super(props, context);
    }

    renderGangAff(card){
        var aff = card.gangAffiliation;
        return aff == null || aff == '' ? '-' : aff
    }

    render() {
        return (
            <div>
                <Panel bsStyle="primary">
                    <Panel.Heading>
                        <Panel.Title componentClass="h3">{this.props.card.name} ({this.renderGangAff(this.props.card)
                      })</Panel.Title>

                    </Panel.Heading>
                    <Panel.Body>
                        <div className="col-md-4">{this.props.card.activation}</div>
                        <div className="col-md-8">{this.props.card.description}</div>
                    </Panel.Body>
                </Panel>
            </div>
        )


    }
}

export default TactiCardPanel;