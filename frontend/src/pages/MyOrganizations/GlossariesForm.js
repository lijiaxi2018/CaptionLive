import { useState, useRef, forwardRef } from 'react';
import { usePostGlossariesMutation } from '../../services/glossary';
import { Form, ButtonToolbar, Button, Input, Schema } from 'rsuite';
import "rsuite/dist/rsuite.css";

/**
 * 
 * reference: https://codesandbox.io/s/swhwiy?file=/index.js:1703-1728
 */

const TextField = forwardRef((props, ref) => {
    const { name, label, accepter, ...rest } = props;
    return (
        <Form.Group controlId={`${name}-4`} ref={ref}>
            <Form.ControlLabel>{label}</Form.ControlLabel>
            <Form.Control name={name} accepter={accepter} {...rest}/>
        </Form.Group>
    ); 
});

const { StringType, NumberType } = Schema.Types;
const model = Schema.Model({
    source: StringType().isRequired('This field is required'),
    romanization: StringType().isRequired('This field is required'),
    term: StringType().isRequired('This field is required'),
    explanation: StringType().isRequired('This field is required'),
    category: StringType().isRequired('This field is required'),
})

const GlossariesForm = ({
    organizationId,
    source='',
    romanization='',
    term='',
    explanation='',
    remark='',
    category='',
    editMode=0 //0 for add glossary, 1 for update glossary
}) => {
    const formRef = useRef();
    const [formValue, setFormValue] = useState({
        organizationId: organizationId,
        source: source,
        romanization: romanization,
        term: term,
        explanation: explanation,
        remark: remark,
        category: category,
        createdTime: new Date(),
        lastUpdatedTime: new Date(),
    })
    const [formError, setFormError] = useState({});

    const [postGlossary] = usePostGlossariesMutation();

    const handleSubmit = () => {
        console.log(`submit mode: ${editMode}`);
        console.log(formValue);
    }
    
    return (
        <div className='glossary-form'>
          <Form
            ref={formRef}
            onChange={setFormValue}
            onCheck={setFormError}
            formValue={formValue}
            model={model}
          >
            <TextField name="source" label="source" />
            <TextField name="romanization" label="romanization" />
            <TextField name="term" label="term" />
            <TextField name="explanation" label="explanation" />
            <TextField name="remark" label="explanation" />
            <TextField name="category" label="explanation" />
          </Form>
          <Button onClick={handleSubmit}>Submit</Button>
        </div>
    )
}

export default GlossariesForm;