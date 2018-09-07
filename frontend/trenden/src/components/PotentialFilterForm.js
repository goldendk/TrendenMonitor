import React, {Component} from 'react';
import {FormGroup, ControlLabel, Radio,Button, Checkbox, FormControl} from 'react-bootstrap'

class PotentialFilterForm extends Component {

    constructor(props, context) {
        super(props, context);
    }

    render() {
        return (<form id={this.props.formId} name={this.props.formId} method={"GET"}
                      onSubmit={this.props.submitFunction}>




            <FormGroup md={6}>
                <ControlLabel>Maximum stock price</ControlLabel>
                <FormControl type="text"
                             id="maxStockPrice"
                             name="maxStockPrice"
                             value="40000"/>
            </FormGroup>

            <FormGroup>
                <ControlLabel>Fees</ControlLabel>
                <div>
                Percentage <FormControl inline type="feePercentage"
                                 className="inline-input-small"
                                 name="feePercentage"
                                 value="0.1"/>%, minimum <FormControl id={"feeMinimum"}
                                                                      className="inline-input-small"
                                                                      name={"feeMinimum"}
                                                                      type="text"
                                                                      inline
                                                                      value="29"/>
                </div>
            </FormGroup>

            <FormGroup >
                <ControlLabel>Exclude shortings</ControlLabel>
                <Checkbox id="excludeShorting" name="excludeShorting" checked/>
            </FormGroup>
            <Button type="submit">Submit</Button>

        </form>);

    }
}

export default PotentialFilterForm;