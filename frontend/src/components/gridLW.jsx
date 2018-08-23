import React, {Component} from 'react';
import { Jumbotron, Grid, Row, Col, Button } from 'react-bootstrap';


class GridLW extends Component {
    render() {
        return (
            <Grid>
                <Jumbotron>
                    <h2>Łukasz Wełnicki Software</h2>
                    <p>Project created for the purpose of MSc diploma thesis</p>
                </Jumbotron>
            </Grid>
        );
    }
}

export default GridLW;