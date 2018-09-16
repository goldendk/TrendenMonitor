import React, {Component} from 'react';
import {FormGroup, ControlLabel, Radio, Button, Checkbox, FormControl, Panel, Glyphicon} from 'react-bootstrap'

class TacticCardPanel extends Component {

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
                <Panel bsStyle={this.props.used ? 'danger' : 'primary'}>
                    <Panel.Heading>
                        <Panel.Title componentClass="h3">{this.props.card.name} ({this.renderGangAff(this.props.card)})
                            <div className={"flex-div"} style={{float: "right", cursor: "pointer"}}>
                                <Glyphicon alt={"Click to undo draw."}
                                           onClick={()=>this.props.toggleCardUse(this.props.card.id, !this.props.used, ()=>console.log("use card ok") ,()=>console.log("use card err"))} style={{float: "right"}}
                                           glyph={ this.props.used ? "remove" : "ok"}/>
                            </div>

                        </Panel.Title>

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

export default TacticCardPanel;